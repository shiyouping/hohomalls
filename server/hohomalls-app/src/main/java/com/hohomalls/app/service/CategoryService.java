package com.hohomalls.app.service;

import com.hohomalls.app.document.Category;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * CategoryService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface CategoryService {

  Mono<List<Category>> findAll();
}
