package com.hohomalls.web.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * This class is defined this way just to align with GraphQLError, so that all the error responses
 * have a unified data structure.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 21/7/2021
 */
@Data
@Builder
@AllArgsConstructor
public class HttpError {

  private final String message = UUID.randomUUID().toString();
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
    private String errorDetail;
    private Origin origin;
  }
}
