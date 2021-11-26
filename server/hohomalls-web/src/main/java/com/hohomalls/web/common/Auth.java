package com.hohomalls.web.common;

/**
 * The interface of Auth.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 1/7/2021
 */
public final class Auth {

  public static final String SELLER = "hasRole('ROLE_SELLER')";
  public static final String BUYER = "hasRole('ROLE_BUYER')";
  public static final String ANONYMOUS = "hasRole('ROLE_ANONYMOUS')";

  private Auth() {}
}
