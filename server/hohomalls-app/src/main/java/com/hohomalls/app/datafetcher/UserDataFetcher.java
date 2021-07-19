package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.CreateUserDto;
import com.hohomalls.app.graphql.types.CredentialsDto;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.web.common.Auth;
import com.hohomalls.web.service.SessionService;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.util.HttpHeaderUtil;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Mono;

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
  @PreAuthorize(Auth.ANONYMOUS)
  public Mono<String> signIn(@InputArgument("credentials") CredentialsDto credentialsDto) {
    return this.userService
        .findOneByEmail(credentialsDto.getEmail())
        .switchIfEmpty(Mono.error(new DgsBadRequestException("Invalid credentials")))
        .flatMap(
            user -> {
              if (!this.passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                return Mono.error(new DgsBadRequestException("Invalid credentials"));
              }

              var token =
                  this.tokenService.getToken(user.getEmail(), user.getNickname(), user.getRole());
              if (token.isEmpty()) {
                return Mono.error(new RuntimeException("Failed to generate JWT"));
              }

              return this.sessionService.save(token.get());
            });
  }

  /**
   * DGS framework has a bug so non-anonymous roles do not work for the time being.<br>
   * See details at https://github.com/Netflix/dgs-framework/issues/458
   */
  @DgsMutation
  // FIXME change the auth role
  @PreAuthorize(Auth.ANONYMOUS)
  public Mono<Void> signOut(@RequestHeader String authorization) {
    var token = HttpHeaderUtil.getAuth(authorization);
    if (token.isEmpty()) {
      return Mono.error(new DgsBadRequestException("Invalid auth"));
    }

    return this.sessionService.delete(token.get()).then();
  }

  @DgsMutation
  @PreAuthorize(Auth.ANONYMOUS)
  public Mono<String> signUp(@InputArgument("user") CreateUserDto createUserDto) {
    return this.userService
        .save(this.userMapper.toDoc(createUserDto))
        .flatMap(
            user -> {
              var token =
                  this.tokenService.getToken(user.getEmail(), user.getNickname(), user.getRole());
              if (token.isEmpty()) {
                return Mono.error(new RuntimeException("Failed to generate JWT"));
              }

              return this.sessionService.save(token.get());
            });
  }
}
