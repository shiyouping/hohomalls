package com.hohomalls.web.service;

import com.hohomalls.data.common.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveSetOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class of SessionServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 17/7/2021
 */
@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

  private final ReactiveSetOperations<String, String> setOps;

  @Autowired
  public SessionServiceImpl(@NotNull ReactiveRedisTemplate<String, String> redisTemplate) {
    checkNotNull(redisTemplate);
    this.setOps = redisTemplate.opsForSet();
  }

  @Override
  public @NotNull Mono<Boolean> delete(@Nullable String session) {
    if (session == null) {
      return Mono.empty();
    }

    return this.setOps.delete(session);
  }

  @Override
  public @NotNull Mono<String> save(@Nullable String session) {
    if (session == null) {
      return Mono.empty();
    }

    return this.setOps.add(RedisKey.SESSION, session).map(value -> session);
  }
}
