package com.hohomalls.data.service;

import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import static com.hohomalls.data.document.Counter.Type;

/**
 * CounterService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 15/5/2022
 */
public interface CounterService {

  /** Atomic increament & get */
  @NotNull
  Mono<Long> incrementAndGet(@NotNull Type type);
}
