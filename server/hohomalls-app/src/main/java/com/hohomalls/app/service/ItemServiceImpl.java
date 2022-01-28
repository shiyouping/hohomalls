package com.hohomalls.app.service;

import com.hohomalls.app.document.Item;
import com.hohomalls.app.repository.CategoryRepository;
import com.hohomalls.app.repository.ItemRepository;
import com.hohomalls.app.repository.ShopRepository;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.data.util.DocUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;

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
  private final ShopRepository shopRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public @NotNull Mono<Page<Item>> findAllByCategoryId(
      @Nullable String categoryId, @NotNull Pageable pageable) {
    ItemServiceImpl.log.info("Finding an item by categoryId={}, pageable={}", categoryId, pageable);
    checkNotNull(pageable, "pageable cannot be null");

    if (categoryId == null) {
      return Mono.empty();
    }

    var example = Example.of(Item.builder().categoryId(categoryId).build());
    return this.itemRepository
        .findAllByCategoryId(categoryId, pageable)
        .collectList()
        .zipWith(this.itemRepository.count(example))
        .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
  }

  @Override
  public @NotNull Mono<Page<Item>> findAllByPhrase(
      @Nullable String phrase, @NotNull Pageable pageable) {
    ItemServiceImpl.log.info("Finding all items by phrase={}, pageable={}", phrase, pageable);
    checkNotNull(pageable, "pageable cannot be null");

    if (phrase == null) {
      return Mono.empty();
    }

    var criteria = TextCriteria.forDefaultLanguage().caseSensitive(false).matchingPhrase(phrase);
    return this.itemRepository
        .findAllBy(criteria, pageable)
        .collectList()
        .zipWith(this.itemRepository.count(criteria))
        .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
  }

  @Override
  public @NotNull Mono<Page<Item>> findAllByShopId(
      @Nullable String shopId, @NotNull Pageable pageable) {
    ItemServiceImpl.log.info("Finding an item by shopId={}, pageable={}", shopId, pageable);
    checkNotNull(pageable, "pageable cannot be null");

    if (shopId == null) {
      return Mono.empty();
    }

    var example = Example.of(Item.builder().shopId(shopId).build());
    return this.itemRepository
        .findAllByShopId(shopId, pageable)
        .collectList()
        .zipWith(this.itemRepository.count(example))
        .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
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
  public @NotNull Mono<Item> save(@NotNull Item item, @NotNull String sellerId) {
    ItemServiceImpl.log.info("Saving an item={}, sellerId={}", item, sellerId);

    // noinspection ConstantConditions
    if (item == null || sellerId == null) {
      return Mono.error(new InvalidInputException("Item or sellerId is null"));
    }

    DocUtil.updateDateTime(item);

    return this.validateShopId(item.getShopId(), sellerId)
        .and(this.validateCategoryId(item.getCategoryId()))
        .then(this.itemRepository.save(item));
  }

  private Mono<Void> validateCategoryId(String id) {
    return this.categoryRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new InvalidInputException("Invalid categoryId %s".formatted(id))))
        .then();
  }

  private Mono<Void> validateShopId(String shopId, String sellerId) {
    return this.shopRepository
        .findById(shopId)
        .switchIfEmpty(Mono.error(new InvalidInputException("Invalid shopId %s".formatted(shopId))))
        .map(
            shop -> {
              if (sellerId.equals(shop.getSellerId())) {
                return shop;
              }

              throw new InvalidInputException("Current user doesn't own this item");
            })
        .then();
  }
}
