package com.hohomalls.app.datafetcher;

import com.hohomalls.app.enumeration.UserStatus;
import com.hohomalls.app.model.UserModel;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import reactor.core.publisher.Mono;

/**
 * The class of UserDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/5/2021
 */
@DgsComponent
public class UserDataFetcher {

  @DgsQuery
  public Mono<UserModel> findOneUser() {
    return Mono.just(
        UserModel.builder()
            .email("ricky@gmail.com")
            .mobile("+8522222")
            .nickname("Ricky")
            .status(UserStatus.ACTIVE)
            .build());
  }
}
