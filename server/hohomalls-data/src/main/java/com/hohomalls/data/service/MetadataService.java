package com.hohomalls.data.service;

import com.hohomalls.data.document.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * MetadataService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public interface MetadataService {

  @NotNull
  Mono<Metadata> findByNameAndPath(@Nullable String name, @Nullable String path);

  @NotNull
  Mono<Metadata> save(@NotNull Metadata metadata);
}
