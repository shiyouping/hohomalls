package com.hohomalls.app.repository;

import com.hohomalls.app.document.Category;
import com.hohomalls.data.repository.BaseDocRepository;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

/**
 * The repository of Category documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface CategoryRepository extends BaseDocRepository<Category> {

  @NonNull
  Flux<Category> findAllByParentId(@NonNull String parentId);

  @NonNull
  Flux<Category> findAllByParentIdIsNull();
}
