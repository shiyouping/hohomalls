package com.hohomalls.app.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.core.common.CacheName;
import com.hohomalls.core.service.DirectoryService;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.cache.CacheFlux;
import reactor.core.publisher.Flux;

/**
 * DirectoryServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 14/1/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

  private final CategoryRepository categoryRepository;
  private final Cache<String, Object> cache = Caffeine.newBuilder().build();

  @Override
  @Cacheable(CacheName.ROOT_DIRECTORIES)
  public @NotNull Flux<String> getRootDirectories() {
    DirectoryServiceImpl.log.info("Getting all root directories...");
    return CacheFlux.lookup(this.cache.asMap(), CacheName.ROOT_DIRECTORIES, String.class)
        .onCacheMissResume(this.categoryRepository.findAllByParentIdIsNull().map(BaseDoc::getId));
  }

  @Override
  @Cacheable(CacheName.SUB_DIRECTORIES)
  public @NotNull Flux<String> getSubDirectories() {
    DirectoryServiceImpl.log.info("Getting all sub directories...");
    return null;
  }
}
