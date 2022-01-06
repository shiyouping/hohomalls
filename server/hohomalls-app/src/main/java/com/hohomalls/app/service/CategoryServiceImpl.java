package com.hohomalls.app.service;

import com.hohomalls.app.document.Category;
import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.core.exception.InternalServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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

  @Override
  public Mono<List<Category>> findAll() {
    CategoryServiceImpl.log.info("Finding all categories");

    return this.categoryRepository
        .findAll()
        .collectList()
        .switchIfEmpty(Mono.error(new InternalServerException("No categories found in mongodb.")))
        .flatMap(categories -> {});
  }
}
