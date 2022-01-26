package com.hohomalls.app.service;

import com.hohomalls.app.document.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
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
  Flux<Item> findAllByCategoryId(@Nullable String categoryId, @Nullable Pageable pageable);

  @NotNull
  Flux<Item> findAllByPhrase(@Nullable String phrase, @Nullable Pageable pageable);

  @NotNull
  Flux<Item> findAllByShopId(@Nullable String shopId, @Nullable Pageable pageable);

  @NotNull
  Mono<Item> findById(@Nullable String id);

  @NotNull
  Mono<Item> save(@NotNull Item item);
}
