package com.hohomalls.core.common;

/**
 * The class of Constant.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/6/2021
 */
public final class Constant {

  public static final String SIGN_COMMA = ",";
  public static final String SIGN_PERIOD = ".";
  public static final String SIGN_SLASH = "/";

  public static final String MEDIA_TYPE_IMAGE = "image";
  public static final String MEDIA_TYPE_VIDEO = "video";

  public static final String JWT_USER_ID = "userId";
  public static final String JWT_EMAIL = "email";
  public static final String JWT_ROLES = "roles";
  public static final String JWT_NICKNAME = "nickname";
  public static final String JWT_SUBJECT = "sub";

  public static final String CONFIG_BASE_PACKAGE = "com.hohomalls";
  public static final String CONFIG_PROPERTY_PREFIX = "com.hohomalls.";

  public static final String PROFILE_NON_PROD = "!prod";
  public static final String PROFILE_PROD = "prod";

  public static final String AUTH_PREFIX = "Bearer";
  public static final String AUTH_ANONYMOUS = "ANONYMOUS";

  private Constant() {}
}
