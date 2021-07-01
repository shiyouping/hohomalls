package com.hohomalls.web.common;

/**
 * The interface of Authorization.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 1/7/2021
 */
public interface Authorization {
  String SELLER = "hasRole('ROLE_SELLER')";
  String BUYER = "hasRole('ROLE_BUYER')";
  String PUBLIC = "permitAll";
}
