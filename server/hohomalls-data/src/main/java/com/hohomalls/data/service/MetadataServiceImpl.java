package com.hohomalls.data.service;

import com.hohomalls.data.document.Metadata;
import com.hohomalls.data.repository.MetadataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * MetadataServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {

  private final MetadataRepository metadataRepository;

  @Override
  public @NotNull Mono<Metadata> findByNameAndPath(@Nullable String name, @Nullable String path) {
    MetadataServiceImpl.log.info("Finding a metadata by name={} and path={}", name, path);
    return this.metadataRepository.findByNameAndPath(name, path);
  }

  @Override
  public @NotNull Mono<Metadata> save(@NotNull Metadata metadata) {
    MetadataServiceImpl.log.info("Saving a metadata={}", metadata);

    if (metadata.getId() == null) {
      metadata.setCreatedAt(Instant.now());
    } else {
      metadata.setUpdatedAt(Instant.now());
    }

    return this.metadataRepository.save(metadata);
  }
}
