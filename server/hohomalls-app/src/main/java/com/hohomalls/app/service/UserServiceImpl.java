package com.hohomalls.app.service;

import com.hohomalls.app.document.User;
import com.hohomalls.app.enumeration.UserStatus;
import com.hohomalls.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * The UserService implementation.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public @NotNull Flux<User> findAllByMobile(@Nullable String mobile) {
    log.info("Finding users by mobile={}", mobile);

    if (mobile == null) {
      return Flux.empty();
    }

    return this.userRepository.findAll(Example.of(User.builder().mobile(mobile).build()));
  }

  @Override
  public @NotNull Mono<User> findOneByEmail(@Nullable String email) {
    log.info("Finding a user by email={}", email);

    if (email == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().email(email).build()));
  }

  @Override
  public @NotNull Mono<User> findOneById(@Nullable String id) {
    log.info("Finding a user by id={}", id);

    if (id == null) {
      return Mono.empty();
    }

    return this.userRepository.findById(id);
  }

  @Override
  public @NotNull Mono<User> findOneByNickname(@Nullable String nickname) {
    log.info("Finding a user by nickname={}", nickname);

    if (nickname == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().nickname(nickname).build()));
  }

  @Override
  public @NotNull Mono<User> save(@NotNull User user) {
    log.info("Saving a user={}", user);

    if (user == null) {
      return Mono.error(new IllegalArgumentException("user is null"));
    }

    Instant now = Instant.now();
    user.setCreatedAt(now);
    user.setUpdatedAt(now);
    user.setStatus(UserStatus.ACTIVE);

    return this.userRepository.save(user);
  }
}
