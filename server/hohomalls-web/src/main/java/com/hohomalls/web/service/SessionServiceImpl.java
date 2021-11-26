package com.hohomalls.web.service;

import com.hohomalls.web.property.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class of SessionServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 17/7/2021
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

  private final TokenProperties tokenProperties;
  private final ReactiveRedisTemplate<String, Authentication> redisTemplate;

  @Override
  public @NotNull Mono<Boolean> delete(@Nullable String session) {
    if (session == null) {
      return Mono.just(false);
    }

    return this.redisTemplate.delete(session).map(value -> value > 0);
  }

  @Override
  public @NotNull Mono<Authentication> getAuthentication(@Nullable String session) {
    if (session == null) {
      return Mono.empty();
    }

    return this.redisTemplate.opsForValue().get(session);
  }

  @Override
  public @NotNull Mono<Boolean> has(@Nullable String session) {
    if (session == null) {
      return Mono.just(false);
    }

    return this.redisTemplate.hasKey(session);
  }

  @Override
  public @NotNull Mono<String> save(
      @Nullable String session, @NotNull Authentication authentication) {
    if (session == null) {
      return Mono.empty();
    }

    checkNotNull(authentication, "authentication cannot be null");

    // Supports multilogin
    return this.redisTemplate
        .opsForValue()
        .set(session, authentication, Duration.ofHours(this.tokenProperties.getLifespan()))
        .map(value -> session);
  }
}
