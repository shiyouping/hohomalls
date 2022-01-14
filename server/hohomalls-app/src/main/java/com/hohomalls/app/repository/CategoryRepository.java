package com.hohomalls.app.repository;

import com.hohomalls.app.document.Category;
import com.hohomalls.data.repository.BaseDocRepository;
import reactor.core.publisher.Flux;

/**
 * The repository of Category documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface CategoryRepository extends BaseDocRepository<Category> {

  Flux<Category> findAllByParentIdIsNull();
}
