package com.hohomalls.app.service;

import com.hohomalls.app.document.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service of user pojo.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface UserService {

  @NotNull
  Flux<User> findAllByMobile(@Nullable String mobile);

  @NotNull
  Mono<User> findOneByEmail(@Nullable String email);

  @NotNull
  Mono<User> findOneById(@Nullable String id);

  @NotNull
  Mono<User> findOneByNickname(@Nullable String nickname);

  @NotNull
  Mono<User> save(@NotNull User user);
}
