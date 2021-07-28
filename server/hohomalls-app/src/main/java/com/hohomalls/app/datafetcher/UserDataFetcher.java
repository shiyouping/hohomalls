package com.hohomalls.app.datafetcher;

import com.hohomalls.app.document.User;
import com.hohomalls.app.graphql.types.CreateUserDto;
import com.hohomalls.app.graphql.types.CredentialsDto;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.web.aop.HasAnyRoles;
import com.hohomalls.web.common.Role;
import com.hohomalls.web.service.SessionService;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.util.AuthorityUtil;
import com.hohomalls.web.util.HttpHeaderUtil;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

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
  @HasAnyRoles(Role.ROLE_ANONYMOUS)
  public Mono<String> signIn(@InputArgument("credentials") CredentialsDto credentialsDto) {
    return this.userService
        .findOneByEmail(credentialsDto.getEmail())
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid credentials")))
        .flatMap(
            user -> {
              if (!this.passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                return Mono.error(new DgsBadRequestException("Invalid credentials"));
              }

              return createSession(user);
            });
  }

  @DgsMutation
  @HasAnyRoles({Role.ROLE_BUYER, Role.ROLE_SELLER})
  public Mono<Void> signOut(@RequestHeader String authorization) {
    var token = HttpHeaderUtil.getAuth(authorization);
    if (token.isEmpty()) {
      return Mono.error(new DgsBadRequestException("Invalid auth"));
    }

    return this.sessionService.delete(token.get()).then();
  }

  @DgsMutation
  @HasAnyRoles(Role.ROLE_ANONYMOUS)
  public Mono<String> signUp(@InputArgument("user") CreateUserDto createUserDto) {
    return this.userService.save(this.userMapper.toDoc(createUserDto)).flatMap(this::createSession);
  }

  @NotNull
  private Mono<String> createSession(@NotNull User user) {
    checkNotNull(user, "user cannot be null");

    var token = this.tokenService.getToken(user.getEmail(), user.getNickname(), user.getRole());
    if (token.isEmpty()) {
      return Mono.error(new RuntimeException("Failed to generate JWT"));
    }

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            user.getEmail(), token.get(), AuthorityUtil.getAuthorityList(List.of(user.getRole())));
    return this.sessionService.save(token.get(), authentication);
  }
}
