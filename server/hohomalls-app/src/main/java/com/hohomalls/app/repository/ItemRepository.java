package com.hohomalls.app.repository;

import com.hohomalls.app.document.Item;
import com.hohomalls.data.repository.BaseDocRepository;
import org.springframework.data.mongodb.core.query.TextCriteria;
import reactor.core.publisher.Flux;

/**
 * The repository of Item documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface ItemRepository extends BaseDocRepository<Item> {

  Flux<Item> findAllBy(TextCriteria criteria);

  Flux<Item> findAllByCategoryId(String categoryId);

  Flux<Item> findAllByShopId(String shopId);
}
