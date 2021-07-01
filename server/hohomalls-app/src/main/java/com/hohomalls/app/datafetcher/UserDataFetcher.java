package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.CreateUserModel;
import com.hohomalls.app.graphql.types.CredentialsModel;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.web.common.Authorization;
import com.hohomalls.web.common.Role;
import com.hohomalls.web.service.TokenService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final PasswordEncoder passwordEncoder;

  @DgsQuery
  public Mono<String> signIn(@InputArgument("credentials") CredentialsModel credentialsModel) {
    return this.userService
        .findOneByEmail(credentialsModel.getEmail())
        .map(
            user -> {
              if (!this.passwordEncoder.matches(
                  credentialsModel.getPassword(), user.getPassword())) {
                throw new AccessDeniedException("Invalid email or password");
              }

              return this.tokenService
                  .getToken(user.getEmail(), user.getNickname())
                  .orElseThrow(RuntimeException::new);
            });
  }

  @DgsMutation
  @PreAuthorize(Authorization.PUBLIC)
  public Mono<String> signUp(@InputArgument("user") CreateUserModel createUserModel) {
    return this.userService
        .save(this.userMapper.toDoc(createUserModel))
        .map(
            user ->
                this.tokenService
                    .getToken(user.getEmail(), user.getNickname(), Role.ROLE_BUYER)
                    .orElseThrow(RuntimeException::new));
  }
}
