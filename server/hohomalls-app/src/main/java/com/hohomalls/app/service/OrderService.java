package com.hohomalls.app.service;

import com.hohomalls.app.document.Order;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.hohomalls.app.document.Order.OrderStatus;

/**
 * OrderService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
public interface OrderService {

  Flux<Order> findAll(
      @Nullable String description,
      @Nullable Instant placementStart,
      @Nullable Instant placementEnd,
      @Nullable String buyerId,
      @Nullable OrderStatus status);

  Mono<Order> findByNumber(@Nullable String number);
}
