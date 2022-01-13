package com.hohomalls.web.controller;

import com.hohomalls.core.exception.InvalidRequestException;
import com.hohomalls.web.common.Auth;
import com.hohomalls.web.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

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

  private final FileService fileService;

  @PreAuthorize(Auth.SELLER)
  @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
          // Content length is a little larger than the file size
          // because of the metadata about the request itself
          this.fileService.validate(
              contentLength, Objects.requireNonNull(filePart.headers().getContentType()));

          return DataBufferUtils.join(filePart.content())
              .flatMap(
                  dataBuffer ->
                      this.fileService.save(
                          rootDir, subDir, filePart.filename(), dataBuffer.asByteBuffer().array()));
        });
  }
}
