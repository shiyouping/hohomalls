package com.hohomalls.app.service;

import com.hohomalls.app.document.User;
import com.hohomalls.app.repository.UserRepository;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.core.util.PasswordUtil;
import com.hohomalls.core.util.PhoneUtil;
import com.hohomalls.web.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static com.hohomalls.app.document.User.UserStatus.ACTIVE;

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
  private final PasswordEncoder passwordEncoder;
  private final SessionService sessionService;

  @Override
  public @NotNull Mono<Void> changePassword(
      @NotNull String email, @NotNull String newPassword, @NotNull String oldPassword) {
    UserServiceImpl.log.info("Changing password for {}", email);
    return this.findByEmail(email)
        .switchIfEmpty(
            Mono.error(new InvalidInputException(String.format("Invalid email %s", email))))
        .flatMap(
            user -> {
              if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
                return Mono.error(
                    new InvalidInputException(String.format("Invalid password %s", oldPassword)));
              }

              user.setPassword(newPassword);
              // Require users to login again after password change
              return this.save(user).then(this.sessionService.clear(email));
            })
        .then();
  }

  @Override
  public @NotNull Flux<User> findAllByMobile(@Nullable String mobile) {
    UserServiceImpl.log.info("Finding users by mobile={}", mobile);

    if (mobile == null) {
      return Flux.empty();
    }

    return this.userRepository.findAll(Example.of(User.builder().mobile(mobile).build()));
  }

  @Override
  public @NotNull Mono<User> findByEmail(@Nullable String email) {
    UserServiceImpl.log.info("Finding a user by email={}", email);

    if (email == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().email(email).build()));
  }

  @Override
  public @NotNull Mono<User> findById(@Nullable String id) {
    UserServiceImpl.log.info("Finding a user by id={}", id);

    if (id == null) {
      return Mono.empty();
    }

    return this.userRepository.findById(id);
  }

  @Override
  public @NotNull Mono<User> findByNickname(@Nullable String nickname) {
    UserServiceImpl.log.info("Finding a user by nickname={}", nickname);

    if (nickname == null) {
      return Mono.empty();
    }

    return this.userRepository.findOne(Example.of(User.builder().nickname(nickname).build()));
  }

  @Override
  public @NotNull Mono<User> save(@NotNull User user) {
    UserServiceImpl.log.info("Saving a user={}", user);

    if (user == null) {
      return Mono.error(new InvalidInputException("user is null"));
    }

    if (!EmailValidator.getInstance().isValid(user.getEmail())) {
      return Mono.error(new InvalidInputException("Invalid email"));
    }

    if (user.getMobile() != null && !PhoneUtil.isValid(user.getMobile())) {
      return Mono.error(new InvalidInputException("Invalid phone number"));
    }

    if (!PasswordUtil.isEncoded(user.getPassword())) {
      if (!PasswordUtil.isValid(user.getPassword())) {
        return Mono.error(new InvalidInputException("Invalid password"));
      }

      user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    }

    Instant now = Instant.now();

    if (user.getCreatedAt() == null) {
      user.setCreatedAt(now);
    }

    user.setUpdatedAt(now);
    user.setStatus(ACTIVE);

    return this.userRepository.save(user);
  }
}
