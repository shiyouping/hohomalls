package com.hohomalls.app.datafetcher;

import com.hohomalls.app.document.User;
import com.hohomalls.app.graphql.types.CreateUserModel;
import com.hohomalls.app.graphql.types.UserModel;
import com.hohomalls.app.mapper.UserMapper;
import com.hohomalls.app.service.UserService;
import com.hohomalls.core.util.FutureUtil;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static com.hohomalls.app.graphql.types.UserStatus.ACTIVE;

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

  @DgsMutation
  public CompletableFuture<UserModel> createUser(
      @InputArgument("user") CreateUserModel createUserModel) {
    Mono<User> mono = this.userService.save(this.userMapper.toDoc(createUserModel));
    return FutureUtil.from(mono, this.userMapper::toModel);
  }

  @DgsQuery
  public UserModel findUser() {
    return UserModel.newBuilder().email("ricky@gmail.com").nickname("Ricky").status(ACTIVE).build();
  }
}
