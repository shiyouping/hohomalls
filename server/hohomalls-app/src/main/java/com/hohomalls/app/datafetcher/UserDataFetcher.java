package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.CreateUserModel;
import com.hohomalls.app.graphql.types.CredentialsModel;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.web.service.TokenService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.CompletableFuture;

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
  public CompletableFuture<String> signIn(
      @InputArgument("credentials") CredentialsModel credentialsModel) {
    return this.userService
        .findOneByEmail(credentialsModel.getEmail())
        .toFuture()
        .thenApply(
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
  public CompletableFuture<String> signUp(@InputArgument("user") CreateUserModel createUserModel) {
    return this.userService
        .save(this.userMapper.toDoc(createUserModel))
        .toFuture()
        .thenApply(
            user ->
                this.tokenService
                    .getToken(user.getEmail(), user.getNickname())
                    .orElseThrow(RuntimeException::new));
  }
}
