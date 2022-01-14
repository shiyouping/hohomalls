package com.hohomalls.core.service;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

/**
 * StorageService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public interface StorageService {

  /** Save the data in the designated place. */
  Mono<Void> save(byte[] data, @NotNull String filePath, @NotNull String fileName);
}
