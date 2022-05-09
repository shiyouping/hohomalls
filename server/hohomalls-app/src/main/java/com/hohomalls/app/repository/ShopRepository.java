package com.hohomalls.app.repository;

import com.hohomalls.app.document.Shop;
import com.hohomalls.data.repository.BaseDocRepository;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * The repository of Shop documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface ShopRepository extends BaseDocRepository<Shop> {

  @NonNull
  Mono<Shop> findByName(@NonNull String name);

  @NonNull
  Mono<Shop> findBySellerId(@NonNull String sellerId);
}
