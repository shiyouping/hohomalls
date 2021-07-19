package com.hohomalls.core.common;

/**
 * The interface of Global.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/6/2021
 */
public interface Global {
  String COMMA = ",";

  // JWT related
  String EMAIL = "email";
  String ROLES = "roles";
  String NICKNAME = "nickname";
  String SUBJECT = "sub";

  String BASE_PACKAGE = "com.hohomalls";
  String PROPERTY_PREFIX = "com.hohomalls.";

  String PROFILE_LOCAL = "local";
  String PROFILE_PROD = "prod";

  String AUTH_PREFIX = "Bearer";
  String ANONYMOUS = "ANONYMOUS";
}
