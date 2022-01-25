package com.hohomalls.web.service;

import com.hohomalls.core.common.Constant;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.core.service.DirectoryService;
import com.hohomalls.core.service.StorageService;
import com.hohomalls.core.util.FileUtil;
import com.hohomalls.core.util.HashUtil;
import com.hohomalls.data.document.Metadata;
import com.hohomalls.data.service.MetadataService;
import com.hohomalls.web.config.WebProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.StringJoiner;

/**
 * FileServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 13/1/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

  private final WebProperties webProperties;
  private final StorageService storageService;
  private final MetadataService metadataService;
  private final DirectoryService directoryService;

  @Override
  public @NotNull Mono<String> save(
      @NotNull String rootDir, @NotNull String subDir, @NotNull String fileName, byte[] data) {
    FileServiceImpl.log.info(
        "Saving a file... rootDir={}, subDir={}, fileName={}", rootDir, subDir, fileName);

    if (this.directoryService.getRootDirectories().stream()
        .noneMatch(root -> root.equals(rootDir))) {
      return Mono.error(new InvalidInputException("Invalid rootDir"));
    }

    if (this.directoryService.getSubDirectories().stream().noneMatch(dir -> dir.equals(subDir))) {
      return Mono.error(new InvalidInputException("Invalid subDir"));
    }

    var hash = HashUtil.getMurmur3(data);
    var path = String.join(Constant.SIGN_SLASH, rootDir, subDir);
    var extension =
        FileUtil.getExtension(fileName)
            .orElseThrow(() -> new InvalidInputException("No file extension."));

    var nameJoiner = new StringJoiner(Constant.SIGN_PERIOD).add(hash);
    if (StringUtils.hasText(extension)) {
      nameJoiner.add(extension);
    }

    var name = nameJoiner.toString();
    return this.metadataService
        .findByNameAndPath(name, path)
        .switchIfEmpty(Mono.defer(() -> this.saveFile(data, path, name)))
        .map(this::generateUrl);
  }

  @Override
  @SuppressWarnings("PMD")
  public void validate(long fileSize, @NotNull MediaType mediaType) {
    if (mediaType == null || fileSize <= 0) {
      throw new InvalidInputException("No file uploaded");
    }

    if (!this.webProperties.multipart().contentTypes().contains(mediaType.toString())) {
      throw new InvalidInputException("mediaType %s is not allowed".formatted(mediaType));
    }

    var imageMaxSize = this.webProperties.multipart().maxFileSize().image() * 1024;
    var videoMaxSize = this.webProperties.multipart().maxFileSize().video() * 1024;

    if (Constant.MEDIA_TYPE_IMAGE.equals(mediaType.getType()) && fileSize > imageMaxSize) {
      throw new InvalidInputException(
          "File size exceeds the limit of %d bytes for images".formatted(imageMaxSize));
    }

    if (Constant.MEDIA_TYPE_VIDEO.equals(mediaType.getType()) && fileSize > videoMaxSize) {
      throw new InvalidInputException(
          "File size  exceeds the limit of %d bytes for video".formatted(videoMaxSize));
    }
  }

  private String generateUrl(Metadata metadata) {
    var url =
        String.join(
            Constant.SIGN_SLASH,
            this.webProperties.multipart().baseStorageUrl(),
            metadata.getPath(),
            metadata.getName());
    FileServiceImpl.log.info("Generated URL = {}", url);
    return url;
  }

  private Mono<Metadata> saveFile(byte[] data, String path, String name) {
    FileServiceImpl.log.info("No same file exists. Continue to save it");
    return this.storageService
        .save(data, path, name)
        .then(this.metadataService.save(Metadata.builder().name(name).path(path).build()));
  }
}
