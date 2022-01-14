package com.hohomalls.app.service;

import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.core.service.DirectoryService;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
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

  @Override
  public @NotNull Flux<String> getRootDirectories() {
    DirectoryServiceImpl.log.info("Finding all root directories...");
    return this.categoryRepository.findAllByParentIdIsNull().map(BaseDoc::getId);
  }

  @Override
  public @NotNull Flux<String> getSubDirectories() {
    return null;
  }
}
