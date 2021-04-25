package com.hohomalls.app.service;

import com.hohomalls.app.document.User;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service of user document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface UserService {

  @NonNull
  Flux<User> findAllByMobile(@Nullable String mobile);

  @NonNull
  Mono<User> findOneByEmail(@Nullable String email);

  @NonNull
  Mono<User> findOneById(@Nullable String id);

  @NonNull
  Mono<User> findOneByNickname(@Nullable String nickname);

  @NonNull
  Mono<User> save(@NonNull User user);
}
