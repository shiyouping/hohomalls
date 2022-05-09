package com.hohomalls.app.repository;

import com.hohomalls.app.document.Item;
import com.hohomalls.data.repository.BaseDocRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

/**
 * The repository of Item documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface ItemRepository extends BaseDocRepository<Item> {

  @NonNull
  Flux<Item> findAllBy(@NonNull TextCriteria criteria, Sort sort);

  @NonNull
  Flux<Item> findAllByCategoryId(@NonNull String categoryId, Sort sort);

  @NonNull
  Flux<Item> findAllByShopId(@NonNull String shopId, Sort sort);
}
