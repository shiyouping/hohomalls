package com.hohomalls.web.handler;

import com.google.common.collect.Maps;
import com.hohomalls.web.common.HttpError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.security.authentication.BadCredentialsException;
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
                          .errorType(this.fromThrowable(t))
                          .origin(HttpError.Origin.APP)
                          .debugInfo(debugInfo)
                          .build())
                  .build()));
    }

    errorAttributes.put(GlobalExceptionAttributes.KEY_STATUS, status);
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

  private HttpError.Type fromThrowable(Throwable throwable) {
    // TODO supplement more here
    if (throwable instanceof BadCredentialsException) {
      return HttpError.Type.UNAUTHENTICATED;
    }

    if (throwable instanceof AuthenticationException) {
      return HttpError.Type.UNAUTHENTICATED;
    }

    if (throwable instanceof ResponseStatusException) {
      return HttpError.Type.NOT_FOUND;
    }

    return HttpError.Type.INTERNAL;
  }
}
