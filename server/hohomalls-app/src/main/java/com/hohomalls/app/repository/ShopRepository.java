package com.hohomalls.app.repository;

import com.hohomalls.app.document.Shop;
import com.hohomalls.data.repository.BaseDocRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The repository of Shop documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface ShopRepository extends BaseDocRepository<Shop> {

  Flux<Shop> findAllBySellerId(String sellerId);

  Mono<Shop> findByName(String name);
}
