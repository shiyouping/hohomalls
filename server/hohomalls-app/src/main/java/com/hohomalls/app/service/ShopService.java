package com.hohomalls.app.service;

import com.hohomalls.app.document.Shop;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * ShopService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface ShopService {

  @NotNull
  Mono<Shop> findById(@Nullable String id);

  @NotNull
  Mono<Shop> findBySellerId(@Nullable String sellerId);

  @NotNull
  Mono<Shop> save(@NotNull Shop shop);
}
