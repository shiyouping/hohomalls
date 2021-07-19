package com.hohomalls.web.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * The interface of SessionService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 16/7/2021
 */
public interface SessionService {

  @NotNull
  Mono<Boolean> delete(@Nullable String session);

  @NotNull
  Mono<Boolean> has(@Nullable String session);

  @NotNull
  Mono<String> save(@Nullable String session);
}
