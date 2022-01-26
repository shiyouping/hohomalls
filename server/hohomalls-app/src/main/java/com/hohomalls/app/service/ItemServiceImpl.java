package com.hohomalls.app.service;

import com.hohomalls.app.document.Item;
import com.hohomalls.app.repository.ItemRepository;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.data.util.DocUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ItemServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  @Override
  public @NotNull Flux<Item> findAllByCategoryId(
      @Nullable String categoryId, @Nullable Pageable pageable) {
    ItemServiceImpl.log.info("Finding an item by categoryId={}, pageable={}", categoryId, pageable);

    if (categoryId == null) {
      return Flux.empty();
    }

    if (pageable == null) {
      return this.itemRepository.findAllByCategoryId(categoryId);
    }

    return this.itemRepository.findAllByCategoryId(categoryId, pageable);
  }

  @Override
  public @NotNull Flux<Item> findAllByPhrase(@Nullable String phrase, @Nullable Pageable pageable) {
    ItemServiceImpl.log.info("Finding all items by phrase={}, pageable={}", phrase, pageable);

    if (phrase == null) {
      return Flux.empty();
    }

    var criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingPhrase(phrase);
    if (pageable == null) {
      return this.itemRepository.findAllBy(criteria);
    }

    return this.itemRepository.findAllBy(criteria, pageable);
  }

  @Override
  public @NotNull Flux<Item> findAllByShopId(@Nullable String shopId, @Nullable Pageable pageable) {
    ItemServiceImpl.log.info("Finding an item by shopId={}, pageable={}", shopId, pageable);

    if (shopId == null) {
      return Flux.empty();
    }

    if (pageable == null) {
      return this.itemRepository.findAllByShopId(shopId);
    }

    return this.itemRepository.findAllByShopId(shopId, pageable);
  }

  @Override
  public @NotNull Mono<Item> findById(@Nullable String id) {
    ItemServiceImpl.log.info("Finding an item by id={}", id);

    if (id == null) {
      return Mono.empty();
    }

    return this.itemRepository.findById(id);
  }

  @Override
  public @NotNull Mono<Item> save(@NotNull Item item) {
    ItemServiceImpl.log.info("Saving an item={}", item);

    if (item == null) {
      return Mono.error(new InvalidInputException("Item is null"));
    }

    DocUtil.updateDateTime(item);
    return this.itemRepository.save(item);
  }
}
