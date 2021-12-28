package com.hohomalls.app.datafetcher;

import com.hohomalls.app.document.User;
import com.hohomalls.app.graphql.types.*;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.core.util.BeanUtil;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.service.SessionService;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.util.AuthorityUtil;
import com.hohomalls.web.util.HttpHeaderUtil;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.hohomalls.core.enumeration.Role.*;

/**
 * The class of UserDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/5/2021
 */
@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

  private final UserMapper userMapper;
  private final UserService userService;
  private final TokenService tokenService;
  private final SessionService sessionService;
  private final PasswordEncoder passwordEncoder;

  @DgsMutation
  @HasAnyRoles({ROLE_SELLER, ROLE_BUYER})
  public Mono<Void> changePassword(
      @RequestHeader String authorization,
      @InputArgument("password") ChangePasswordDto changePasswordDto) {
    if (changePasswordDto.getAfter().equals(changePasswordDto.getBefore())) {
      return Mono.error(new DgsBadRequestException("Password doesn't change"));
    }

    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService.changePassword(
        email.get(), changePasswordDto.getAfter(), changePasswordDto.getBefore());
  }

  @DgsQuery
  @HasAnyRoles({ROLE_BUYER, ROLE_SELLER})
  public Mono<UserDto> findUser(@RequestHeader String authorization) {
    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService
        .findOneByEmail(email.get())
        .flatMap(user -> Mono.justOrEmpty(this.userMapper.toDto(user)));
  }

  @DgsMutation
  @HasAnyRoles(ROLE_ANONYMOUS)
  public Mono<String> signIn(@InputArgument("credentials") CredentialsDto credentialsDto) {
    return this.userService
        .findOneByEmail(credentialsDto.getEmail())
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid credentials")))
        .flatMap(
            user -> {
              if (!this.passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                return Mono.error(new DgsBadRequestException("Invalid credentials"));
              }

              return this.createSession(user);
            });
  }

  @DgsMutation
  @HasAnyRoles({ROLE_SELLER, ROLE_BUYER})
  public Mono<Void> signOut(@RequestHeader String authorization) {
    var token = HttpHeaderUtil.getAuth(authorization);
    return this.sessionService.delete(token.get()).then();
  }

  @SneakyThrows
  @DgsMutation
  @HasAnyRoles(ROLE_ANONYMOUS)
  public Mono<String> signUp(@InputArgument("user") CreateUserDto createUserDto) {
    var roles = createUserDto.getRoles();
    if (!roles.contains(Role.ROLE_BUYER)) {
      // Every new user has the buyer role
      roles.add(Role.ROLE_BUYER);
    }

    if (this.userService.findOneByEmail(createUserDto.getEmail()).toFuture().get() != null) {
      return Mono.error(
          new DgsBadRequestException(
              String.format("Email %s was already registered", createUserDto.getEmail())));
    }

    return this.userService.save(this.userMapper.toDoc(createUserDto)).flatMap(this::createSession);
  }

  @DgsMutation
  @HasAnyRoles({ROLE_SELLER, ROLE_BUYER})
  public Mono<UserDto> updateUser(
      @RequestHeader String authorization, @InputArgument("user") UpdateUserDto updateUserDto) {
    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService
        .findOneByEmail(email.get())
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid credentials")))
        .flatMap(
            user -> {
              BeanUtil.copyNonnullProperties(user, this.userMapper.toDoc(updateUserDto));
              return this.userService.save(user).map(this.userMapper::toDto);
            });
  }

  @NotNull
  private Mono<String> createSession(@NotNull User user) {
    checkNotNull(user, "user cannot be null");

    var token = this.tokenService.getToken(user.getEmail(), user.getNickname(), user.getRoles());
    if (token.isEmpty()) {
      return Mono.error(new RuntimeException("Failed to generate JWT"));
    }

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            user.getEmail(), token.get(), AuthorityUtil.getAuthorityList(user.getRoles()));
    return this.sessionService.save(token.get(), authentication);
  }
}
