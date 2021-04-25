package com.hohomalls.app.service;

import com.hohomalls.app.document.User;
import com.hohomalls.app.repository.UserRepository;
import com.hohomalls.core.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * The UserService implementation.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public @NonNull Flux<User> findAllByMobile(@Nullable String mobile) {
    log.info("Finding users by mobile={}", mobile);

    if (mobile == null) {
      return Flux.empty();
    }

    return this.userRepository.findAll(Example.of(User.builder().mobile(mobile).build()));
  }

  @Override
  public @NonNull Mono<User> findOneByEmail(@Nullable String email) {
    log.info("Finding a user by email={}", email);

    if (email == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().email(email).build()));
  }

  @Override
  public @NonNull Mono<User> findOneById(@Nullable String id) {
    log.info("Finding a user by id={}", id);

    if (id == null) {
      return Mono.empty();
    }

    return this.userRepository.findById(id);
  }

  @Override
  public @NonNull Mono<User> findOneByNickname(@Nullable String nickname) {
    log.info("Finding a user by nickname={}", nickname);

    if (nickname == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().nickname(nickname).build()));
  }

  @Override
  public @NonNull Mono<User> save(@NonNull User user) {
    log.info("Saving or updating a user={}", user);

    // noinspection ConstantConditions
    if (user == null) {
      return Mono.error(new IllegalArgumentException("user is null"));
    }

    user.setStatus(User.UserStatus.ACTIVE);
    user.setCreatedDateTime(DateTimeUtil.now());
    user.setUpdatedDateTime(DateTimeUtil.now());

    return this.userRepository.save(user);
  }
}
