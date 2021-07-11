package com.hohomalls.web.common;

/**
 * The interface of Auth.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 1/7/2021
 */
public interface Auth {
  String SELLER = "hasRole('ROLE_SELLER')";
  String BUYER = "hasRole('ROLE_BUYER')";
  String ANONYMOUS = "hasRole('ROLE_ANONYMOUS')";
}
