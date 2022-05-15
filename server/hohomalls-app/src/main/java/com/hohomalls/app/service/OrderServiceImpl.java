package com.hohomalls.app.service;

import com.hohomalls.app.document.Order;
import com.hohomalls.app.document.Order.OrderStatus;
import com.hohomalls.app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * OrderServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public Flux<Order> findAll(
      @Nullable String description,
      @Nullable Instant placementStart,
      @Nullable Instant placementEnd,
      @Nullable String buyerId,
      @Nullable OrderStatus status) {
    return null;
  }

  @Override
  public @NotNull Mono<Order> findByNumber(@Nullable String number) {
    OrderServiceImpl.log.info("Finding an order by number={}", number);
    return number == null ? Mono.empty() : this.orderRepository.findByNumber(number);
  }

  @Override
  public @NotNull Mono<Order> save(@NotNull Order order) {

    return null;
  }
}
