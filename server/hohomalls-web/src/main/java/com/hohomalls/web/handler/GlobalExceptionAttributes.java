package com.hohomalls.web.handler;

import com.google.common.collect.Maps;
import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.core.exception.InvalidRequestException;
import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.web.common.HttpError;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

/**
 * Customize the error response attributes returned to the client. The error attribute yml
 * configuration from Spring doesn't work anymore.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/6/2021
 */
@Slf4j
@Component
public class GlobalExceptionAttributes extends DefaultErrorAttributes {

  private static final String KEY_ERRORS = "errors";
  private static final String KEY_ERROR = "error";
  private static final String KEY_STATUS = "status";
  private static final String KEY_PATH = "path";
  private static final String ERROR_ATTRIBUTE =
      "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";

  @Override
  public Map<String, Object> getErrorAttributes(
      ServerRequest request, ErrorAttributeOptions options) {

    options.including(EXCEPTION, STACK_TRACE, MESSAGE, BINDING_ERRORS);

    var attributes = super.getErrorAttributes(request, options);
    var status = (Integer) attributes.get(GlobalExceptionAttributes.KEY_STATUS);
    var error = (String) attributes.get(GlobalExceptionAttributes.KEY_ERROR);
    var throwable = request.attribute(GlobalExceptionAttributes.ERROR_ATTRIBUTE);
    var locations = List.of((String) attributes.get(GlobalExceptionAttributes.KEY_PATH));

    Map<String, String> debugInfo = Maps.newHashMapWithExpectedSize(2);
    debugInfo.put(HttpError.DEBUG_INFO_EXCEPTION_ID, UUID.randomUUID().toString());

    Map<String, Object> errorAttributes = Maps.newHashMap();
    status = this.reviseStatus((Throwable) throwable.orElse(null), status);

    if (throwable.isEmpty()) {
      errorAttributes.put(
              GlobalExceptionAttributes.KEY_ERRORS,
          List.of(
              HttpError.builder()
                  .message(error)
                  .locations(locations)
                  .extensions(
                      HttpError.Extensions.builder()
                          .errorType(this.fromStatus(status))
                          .origin(HttpError.Origin.APP)
                          .debugInfo(debugInfo)
                          .build())
                  .build()));
    } else {
      var t = (Throwable) throwable.get();
      debugInfo.put(HttpError.DEBUG_INFO_EXCEPTION_NAME, t.getClass().getSimpleName());

      errorAttributes.put(
              GlobalExceptionAttributes.KEY_ERRORS,
          List.of(
              HttpError.builder()
                  .message(t.getMessage())
                  .locations(locations)
                  .extensions(
                      HttpError.Extensions.builder()
                          .errorType(this.fromStatus(status))
                          .origin(HttpError.Origin.APP)
                          .debugInfo(debugInfo)
                          .build())
                  .build()));
    }

    // Always return 200 to comply with GraphQL standard
    errorAttributes.put(GlobalExceptionAttributes.KEY_STATUS, 200);
    GlobalExceptionAttributes.log.error("Cannot proceed. ErrorAttributes={}", errorAttributes);
    return errorAttributes;
  }

  private HttpError.Type fromStatus(int status) {
    return switch (status) {
      case 400 -> HttpError.Type.BAD_REQUEST;
      case 412 -> HttpError.Type.FAILED_PRECONDITION;
      case 500 -> HttpError.Type.INTERNAL;
      case 404 -> HttpError.Type.NOT_FOUND;
      case 401 -> HttpError.Type.PERMISSION_DENIED;
      case 403 -> HttpError.Type.UNAUTHENTICATED;
      case 503 -> HttpError.Type.UNAVAILABLE;
      default -> HttpError.Type.UNKNOWN;
    };
  }

  private int reviseStatus(Throwable throwable, int status) {
    if (throwable == null) {
      return status;
    }

    if (throwable instanceof InvalidInputException
            || throwable instanceof InvalidRequestException
            || throwable instanceof DgsBadRequestException) {
      return HttpStatus.BAD_REQUEST.value();
    }

    if (throwable instanceof AuthenticationException
            || throwable instanceof InvalidTokenException) {
      return HttpStatus.UNAUTHORIZED.value();
    }

    if (throwable instanceof ResponseStatusException) {
      return HttpStatus.NOT_FOUND.value();
    }

    return HttpStatus.INTERNAL_SERVER_ERROR.value();
  }
}
