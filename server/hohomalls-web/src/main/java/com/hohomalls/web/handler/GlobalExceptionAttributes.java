package com.hohomalls.web.handler;

import com.google.common.collect.Maps;
import com.hohomalls.web.common.HttpError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.List;
import java.util.Map;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.*;

/**
 * Customize the error response attributes returned to the client. The error attribute yml
 * configuration from Spring doesn't work any more.
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
  private static final String ERROR_ATTRIBUTE =
      "org.springframework.boot.web.reactive.error.DefaultErrorAttributes.ERROR";

  @Override
  public Map<String, Object> getErrorAttributes(
      ServerRequest request, ErrorAttributeOptions options) {

    options.including(EXCEPTION, STACK_TRACE, MESSAGE, BINDING_ERRORS);
    var attributes = super.getErrorAttributes(request, options);
    var status = (Integer) attributes.get(KEY_STATUS);
    var error = (String) attributes.get(KEY_ERROR);
    var throwable = request.attribute(ERROR_ATTRIBUTE);
    Map<String, Object> errorAttributes = Maps.newHashMap();

    if (throwable.isEmpty()) {
      errorAttributes.put(
          KEY_ERRORS,
          List.of(
              HttpError.builder()
                  .extensions(
                      HttpError.Extensions.builder()
                          .errorType(fromStatus(status))
                          .errorDetail(error)
                          .origin(HttpError.Origin.UNKNOWN)
                          .build())
                  .build()));
    } else {
      var t = (Throwable) throwable.get();
      errorAttributes.put(
          KEY_ERRORS,
          List.of(
              HttpError.builder()
                  .extensions(
                      HttpError.Extensions.builder()
                          .errorType(fromThrowable(t))
                          .errorDetail(t.getMessage())
                          .build())
                  .build()));
    }

    errorAttributes.put(KEY_STATUS, status);
    log.error("Cannot proceed. ErrorAttributes={}", errorAttributes);
    return errorAttributes;
  }

  private HttpError.Type fromStatus(int status) {
    switch (status) {
      case 400:
        return HttpError.Type.BAD_REQUEST;
      case 412:
        return HttpError.Type.FAILED_PRECONDITION;
      case 500:
        return HttpError.Type.INTERNAL;
      case 404:
        return HttpError.Type.NOT_FOUND;
      case 401:
        return HttpError.Type.PERMISSION_DENIED;
      case 403:
        return HttpError.Type.UNAUTHENTICATED;
      case 503:
        return HttpError.Type.UNAVAILABLE;
      default:
        return HttpError.Type.UNKNOWN;
    }
  }

  private HttpError.Type fromThrowable(Throwable throwable) {
    // TODO supplement more here
    if (throwable instanceof AuthenticationException) {
      return HttpError.Type.UNAUTHENTICATED;
    }

    return HttpError.Type.INTERNAL;
  }
}
