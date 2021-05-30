package com.hohomalls.app.datafetcher;

import com.hohomalls.app.graphql.types.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

/**
 * The class of UserDataFetcher.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/5/2021
 */
@DgsComponent
public class UserDataFetcher {

  @DgsQuery
  public User findOneUser() {
    return null;
  }
}
