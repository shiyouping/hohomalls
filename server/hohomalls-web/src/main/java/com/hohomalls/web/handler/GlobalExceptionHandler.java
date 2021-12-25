package com.hohomalls.web.handler;

import com.hohomalls.core.exception.InvalidInputException;
import com.hohomalls.web.common.HttpError;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import com.netflix.graphql.types.errors.TypedGraphQLError.Builder;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.execution.ResultPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Handles exceptions from data fetchers. Any RuntimeException is translated to a GraphQLError of
 * type INTERNAL. For some specific exception types, a more specific GraphQL error type is used.
 *
 * <p>See details at https://netflix.github.io/dgs/error-handling/
 *
 * @author ricky.shiyouping@gmail.com
 * @since 31/5/2021
 */
@Slf4j
@Component
public class GlobalExceptionHandler implements DataFetcherExceptionHandler {

  @Override
  public DataFetcherExceptionHandlerResult onException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    var exception = handlerParameters.getException();
    var path = handlerParameters.getPath();
    GraphQLError error;

    if (exception instanceof AccessDeniedException) {
      error = getGraphqlError(TypedGraphQLError.newPermissionDeniedBuilder(), path, exception);
    } else if (exception instanceof DgsEntityNotFoundException) {
      error = getGraphqlError(TypedGraphQLError.newNotFoundBuilder(), path, exception);
    } else if (exception instanceof DgsBadRequestException
        || exception instanceof DgsInvalidInputArgumentException
        || exception instanceof InvalidInputException) {
      error = getGraphqlError(TypedGraphQLError.newBadRequestBuilder(), path, exception);
    } else {
      error = getGraphqlError(TypedGraphQLError.newInternalErrorBuilder(), path, exception);
    }

    return DataFetcherExceptionHandlerResult.newResult().error(error).build();
  }

  private GraphQLError getGraphqlError(Builder builder, ResultPath path, Throwable exception) {
    var id = UUID.randomUUID().toString();

    if (log.isErrorEnabled()) {
      log.error(
          String.format(
              "Failed to execute data fetcher for %s: %s. ErrorId=%s",
              path, exception.getMessage(), id),
          exception);
    }

    // The data structure should be consistent with HttpError
    return builder
        .message(exception.getMessage())
        .debugInfo(
            Map.of(
                HttpError.DEBUG_INFO_EXCEPTION_ID,
                id,
                HttpError.DEBUG_INFO_EXCEPTION_NAME,
                exception.getClass().getSimpleName()))
        .path(path)
        .origin(HttpError.Origin.WEB.name())
        .build();
  }
}
