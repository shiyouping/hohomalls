package com.hohomalls.app.service;

import com.hohomalls.app.document.Shop;
import com.hohomalls.app.repository.ShopRepository;
import com.hohomalls.app.repository.UserRepository;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.data.util.DocUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * ShopServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

  private final ShopRepository shopRepository;
  private final UserRepository userRepository;

  @Override
  public @NotNull Mono<Shop> findById(@Nullable String id) {
    ShopServiceImpl.log.info("Finding a shop by id={}", id);

    if (id == null) {
      return Mono.empty();
    }

    return this.shopRepository.findById(id);
  }

  @Override
  public @NotNull Mono<Shop> findBySellerId(@Nullable String sellerId) {
    ShopServiceImpl.log.info("Finding a shop by sellerId={}", sellerId);

    if (sellerId == null) {
      return Mono.empty();
    }

    return this.shopRepository.findBySellerId(sellerId);
  }

  @Override
  public @NotNull Mono<Shop> save(@NotNull Shop shop) {
    ShopServiceImpl.log.info("Saving a shop={}", shop);

    if (shop == null || shop.getSellerId() == null) {
      return Mono.error(new InvalidInputException("Invalid shop"));
    }

    DocUtil.updateDateTime(shop);

    return this.userRepository
        .findById(shop.getSellerId())
        .switchIfEmpty(
            Mono.error(
                new InvalidInputException("Invalid seller id %s".formatted(shop.getSellerId()))))
        .then(this.shopRepository.save(shop));
  }
}
