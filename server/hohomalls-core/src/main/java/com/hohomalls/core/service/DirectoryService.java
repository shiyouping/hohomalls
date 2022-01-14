package com.hohomalls.core.service;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

/**
 * DirectoryService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 14/1/2022
 */
public interface DirectoryService {

  @NotNull
  Flux<String> getRootDirectories();

  @NotNull
  Flux<String> getSubDirectories();
}
