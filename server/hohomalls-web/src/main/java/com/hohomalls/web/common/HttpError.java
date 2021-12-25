package com.hohomalls.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * This class is defined this way just to align with {@link graphql.GraphQLError}, so that all the
 * error responses have a unified data structure.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 21/7/2021
 */
@Data
@Builder
@AllArgsConstructor
public class HttpError {
  public static final String DEBUG_INFO_EXCEPTION_ID = "exceptionId";
  public static final String DEBUG_INFO_EXCEPTION_NAME = "exceptionName";

  private String message;
  private List<String> path;
  private List<String> locations;
  private Extensions extensions;

  public enum Type {
    BAD_REQUEST,
    FAILED_PRECONDITION,
    INTERNAL,
    NOT_FOUND,
    PERMISSION_DENIED,
    UNAUTHENTICATED,
    UNAVAILABLE,
    UNKNOWN
  }

  public enum Origin {
    APP,
    CORE,
    DATA,
    UNKNOWN,
    WEB
  }

  @Data
  @Builder
  @AllArgsConstructor
  public static class Extensions {
    private Type errorType;
    private Origin origin;
    private Map<String, String> debugInfo;
  }
}
