package com.hohomalls.web.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * The interface of SessionService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 16/7/2021
 */
public interface SessionService {

  /** Clear all sessions associated with the given email. */
  @NotNull
  Mono<Boolean> clear(@Nullable String email);

  @NotNull
  Mono<Boolean> delete(@Nullable String session);

  @NotNull
  Mono<Authentication> getAuthentication(@Nullable String session);

  @NotNull
  Mono<Boolean> has(@Nullable String session);

  @NotNull
  Mono<String> save(@Nullable String session, @NotNull Authentication authentication);
}
