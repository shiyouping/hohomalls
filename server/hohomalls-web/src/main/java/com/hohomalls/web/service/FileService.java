package com.hohomalls.web.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

/**
 * FileService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 13/1/2022
 */
public interface FileService {

  /**
   * Save the data in the storage system and keep a record in the database.
   *
   * @return a URL of the saved file
   */
  @NotNull
  Mono<String> save(
      @NotNull String rootDir, @NotNull String subDir, @NotNull String fileName, byte[] data);

  void validate(long size, @NotNull MediaType mediaType);
}
