package com.hohomalls.web.controller;

import com.hohomalls.core.exception.InvalidRequestException;
import com.hohomalls.core.service.StorageService;
import com.hohomalls.core.util.FileUtil;
import com.hohomalls.core.util.HashUtil;
import com.hohomalls.data.document.Metadata;
import com.hohomalls.data.service.MetadataService;
import com.hohomalls.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.StringJoiner;
import java.util.function.Function;

import static com.hohomalls.core.common.Global.*;

/**
 * FileController.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 10/1/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

  private final WebProperties webProperties;
  private final StorageService storageService;
  private final MetadataService metadataService;

  @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<String> upload(
      @RequestHeader("Content-Length") long contentLength,
      @RequestPart("file") Mono<FilePart> fileMono,
      @RequestPart("rootDir") String rootDir,
      @RequestPart("subDir") String subDir) {
    FileController.log.info(
        "Receiving a request to upload a file to rootDir={}, subDir={}", rootDir, subDir);

    if (!StringUtils.hasText(rootDir) || !StringUtils.hasText(subDir)) {
      return Mono.error(new InvalidRequestException("Empty rootDir or subDir"));
    }

    return fileMono.flatMap(
        filePart -> {
          this.checkFile(contentLength, filePart.headers().getContentType());

          var extension =
              FileUtil.getExtension(filePart.filename())
                  .orElseThrow(() -> new InvalidRequestException("No file extension"));

          return DataBufferUtils.join(filePart.content())
              .flatMap(this.getMapper(rootDir, subDir, extension));
        });
  }

  private void checkFile(long contentLength, MediaType contentType) {
    if (contentType == null) {
      throw new InvalidRequestException("No file uploaded");
    }

    if (!this.webProperties.multipart().contentTypes().contains(contentType.toString())) {
      throw new InvalidRequestException("contentType %s is not allowed".formatted(contentType));
    }

    // Content length is a little larger than the file size
    // because of the metadata about the request itself
    var imageMaxSize = this.webProperties.multipart().maxFileSize().image() * 1024;
    var videoMaxSize = this.webProperties.multipart().maxFileSize().video() * 1024;

    if (MEDIA_TYPE_IMAGE.equals(contentType.getType()) && contentLength > imageMaxSize) {
      throw new InvalidRequestException(
          "Content length exceeds the limit of %d bytes for images".formatted(imageMaxSize));

    } else if (MEDIA_TYPE_VIDEO.equals(contentType.getType()) && contentLength > videoMaxSize) {
      throw new InvalidRequestException(
          "Content length exceeds the limit of %d bytes for video".formatted(videoMaxSize));
    }
  }

  private String generateUrl(Metadata metadata) {
    return String.join(
        SIGN_SLASH,
        this.webProperties.multipart().baseStorageUrl(),
        metadata.getPath(),
        metadata.getName());
  }

  private Function<DataBuffer, Mono<? extends String>> getMapper(
      String rootDir, String subDir, String extension) {
    return dataBuffer -> {
      var data = dataBuffer.asByteBuffer().array();
      var hash = HashUtil.getMurmur3(data);
      var path = String.join(SIGN_SLASH, rootDir, subDir);

      var nameJoiner = new StringJoiner(SIGN_PERIOD).add(hash);
      if (StringUtils.hasText(extension)) {
        nameJoiner.add(extension);
      }
      var name = nameJoiner.toString();

      return this.metadataService
          .findByNameAndPath(name, path)
          .switchIfEmpty(Mono.defer(() -> this.saveFile(data, path, name)))
          .map(this::generateUrl);
    };
  }

  private Mono<Metadata> saveFile(byte[] data, String path, String name) {
    this.storageService.save(data, path, name);
    return this.metadataService.save(Metadata.builder().name(name).path(path).build());
  }
}
