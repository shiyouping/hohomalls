package com.hohomalls.app.service;

import com.hohomalls.app.document.Category;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

/**
 * CategoryService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface CategoryService {

  @NotNull
  Flux<Category> findAll();
}
