package com.hohomalls.app.service;

import com.hohomalls.app.document.Category;
import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.core.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * CategoryServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @NotNull
  @Override
  public Flux<Category> findAll() {
    CategoryServiceImpl.log.info("Finding all categories");
    return this.categoryRepository
        .findAll()
        .switchIfEmpty(Flux.error(new InternalServerException("No categories in the database")));
  }
}
