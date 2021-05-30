package com.hohomalls.app.service;

import com.hohomalls.app.document.UserDoc;
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
  Flux<UserDoc> findAllByMobile(@Nullable String mobile);

  @NonNull
  Mono<UserDoc> findOneByEmail(@Nullable String email);

  @NonNull
  Mono<UserDoc> findOneById(@Nullable String id);

  @NonNull
  Mono<UserDoc> findOneByNickname(@Nullable String nickname);

  @NonNull
  Mono<UserDoc> save(@NonNull UserDoc userDoc);
}
