package com.hohomalls.app.service;

import com.hohomalls.app.document.UserDoc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The service of user document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface UserService {

  @NotNull
  Flux<UserDoc> findAllByMobile(@Nullable String mobile);

  @NotNull
  Mono<UserDoc> findOneByEmail(@Nullable String email);

  @NotNull
  Mono<UserDoc> findOneById(@Nullable String id);

  @NotNull
  Mono<UserDoc> findOneByNickname(@Nullable String nickname);

  @NotNull
  Mono<UserDoc> save(@NotNull UserDoc userDoc);
}
