package com.hohomalls.app.service;

import com.hohomalls.app.document.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * ItemService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface ItemService {

  @NotNull
  Mono<Page<Item>> findAllByCategoryId(@Nullable String categoryId, @NotNull Pageable pageable);

  @NotNull
  Mono<Page<Item>> findAllByPhrase(@Nullable String phrase, @NotNull Pageable pageable);

  @NotNull
  Mono<Page<Item>> findAllByShopId(@Nullable String shopId, @NotNull Pageable pageable);

  @NotNull
  Mono<Item> findById(@Nullable String id);

  @NotNull
  Mono<Item> save(@NotNull Item item, @NotNull String sellerId);
}
