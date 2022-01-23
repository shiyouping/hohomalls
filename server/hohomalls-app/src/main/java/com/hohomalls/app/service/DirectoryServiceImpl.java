package com.hohomalls.app.service;

import com.hohomalls.app.document.Category;
import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.core.common.CacheName;
import com.hohomalls.core.service.DirectoryService;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

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

  @Override
  @SneakyThrows
  @Cacheable(CacheName.ROOT_DIRECTORIES)
  public @NotNull List<String> getRootDirectories() {
    DirectoryServiceImpl.log.info("Getting all root directories...");
    return this.categoryRepository
        .findAllByParentIdIsNull()
        .map(BaseDoc::getId)
        .collectList()
        .toFuture()
        .get();
  }

  @Override
  @SneakyThrows
  @Cacheable(CacheName.SUB_DIRECTORIES)
  public @NotNull List<String> getSubDirectories() {
    DirectoryServiceImpl.log.info("Getting all sub directories...");
    return this.categoryRepository
        .findAllByParentIdIsNull()
        .flatMap(this::getSubDirectories)
        .collectList()
        .toFuture()
        .get();
  }

  private Flux<String> getSubDirectories(Category root) {
    return this.categoryRepository.findAllByParentId(root.getId()).map(BaseDoc::getId);
  }
}
