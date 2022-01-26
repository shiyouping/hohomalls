package com.hohomalls.app.datafetcher;

import com.hohomalls.app.document.User;
import com.hohomalls.app.graphql.types.*;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.core.enumeration.Role;
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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

/**
 * The class of UserDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/5/2021
 */
@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

  private final UserMapper userMapper;
  private final UserService userService;
  private final TokenService tokenService;
  private final SessionService sessionService;
  private final PasswordEncoder passwordEncoder;

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER, Role.ROLE_BUYER})
  public Mono<Void> changePassword(
      @RequestHeader String authorization,
      @InputArgument("password") ChangePasswordDto changePasswordDto) {

    UserDataFetcher.log.info(
        "Received a request to change the password. Authorization={}", authorization);

    if (changePasswordDto.getAfter().equals(changePasswordDto.getBefore())) {
      return Mono.error(new DgsBadRequestException("Password doesn't change"));
    }

    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService.changePassword(
        email.get(), changePasswordDto.getAfter(), changePasswordDto.getBefore());
  }

  @DgsQuery
  @HasAnyRoles({Role.ROLE_BUYER, Role.ROLE_SELLER})
  public Mono<UserDto> findUser(@RequestHeader String authorization) {
    UserDataFetcher.log.info(
        "Received a request to find the user. Authorization={}", authorization);

    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService
        .findByEmail(email.get())
        .flatMap(user -> Mono.justOrEmpty(this.userMapper.toDto(user)));
  }

  @DgsMutation
  @HasAnyRoles(Role.ROLE_ANONYMOUS)
  public Mono<String> signIn(@InputArgument("credentials") CredentialsDto credentialsDto) {
    UserDataFetcher.log.info("Received a request to sign in. Email={}", credentialsDto.getEmail());

    return this.userService
        .findByEmail(credentialsDto.getEmail())
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
  @HasAnyRoles({Role.ROLE_SELLER, Role.ROLE_BUYER})
  public Mono<Void> signOut(@RequestHeader String authorization) {
    UserDataFetcher.log.info("Received a request to sign out. Authorization={}", authorization);

    var token = HttpHeaderUtil.getAuth(authorization);
    return this.sessionService.delete(token.get()).then();
  }

  @SneakyThrows
  @DgsMutation
  @HasAnyRoles(Role.ROLE_ANONYMOUS)
  public Mono<String> signUp(@InputArgument("user") CreateUserDto createUserDto) {
    UserDataFetcher.log.info("Received a request to sign up. Email={}", createUserDto.getEmail());

    var roles = createUserDto.getRoles();
    if (!roles.contains(com.hohomalls.app.graphql.types.Role.ROLE_BUYER)) {
      // Every new user has the buyer role
      roles.add(com.hohomalls.app.graphql.types.Role.ROLE_BUYER);
    }

    if (this.userService.findByEmail(createUserDto.getEmail()).toFuture().get() != null) {
      return Mono.error(
          new DgsBadRequestException(
              "Email %s was already registered".formatted(createUserDto.getEmail())));
    }

    return this.userService.save(this.userMapper.toDoc(createUserDto)).flatMap(this::createSession);
  }

  @DgsMutation
  @HasAnyRoles({Role.ROLE_SELLER, Role.ROLE_BUYER})
  public Mono<UserDto> updateUser(
      @RequestHeader String authorization, @InputArgument("user") UpdateUserDto updateUserDto) {
    UserDataFetcher.log.info("Received a request to update user. Authorization={}", authorization);

    var email = this.tokenService.getEmailFromAuth(authorization);
    return this.userService
        .findByEmail(email.get())
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid credentials")))
        .flatMap(
            user -> {
              BeanUtil.copyNonnullProperties(user, this.userMapper.toDoc(updateUserDto));
              return this.userService.save(user).map(this.userMapper::toDto);
            });
  }

  @NotNull
  private Mono<String> createSession(@NotNull User user) {
    var token =
        this.tokenService.generateToken(
            user.getId(), user.getEmail(), user.getNickname(), user.getRoles());
    if (token.isEmpty()) {
      return Mono.error(new RuntimeException("Failed to generate JWT"));
    }

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            user.getEmail(), token.get(), AuthorityUtil.getAuthorityList(user.getRoles()));
    return this.sessionService.save(token.get(), authentication);
  }
}
