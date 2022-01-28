package com.hohomalls.app.service;

import com.hohomalls.app.document.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ItemService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface ItemService {

  @NotNull
  Flux<Item> findAllByCategoryId(@Nullable String categoryId);

  @NotNull
  Flux<Item> findAllByPhrase(@Nullable String phrase);

  @NotNull
  Flux<Item> findAllByShopId(@Nullable String shopId);

  @NotNull
  Mono<Item> findById(@Nullable String id);

  @NotNull
  Mono<Item> save(@NotNull Item item, @NotNull String sellerId);
}
