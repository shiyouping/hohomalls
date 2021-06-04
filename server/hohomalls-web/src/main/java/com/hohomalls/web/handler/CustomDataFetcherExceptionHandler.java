package com.hohomalls.web.handler;

import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
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
public class CustomDataFetcherExceptionHandler implements DataFetcherExceptionHandler {

  @Override
  public DataFetcherExceptionHandlerResult onException(
      DataFetcherExceptionHandlerParameters handlerParameters) {

    Throwable exception = handlerParameters.getException();
    ResultPath path = handlerParameters.getPath();
    String errorId = UUID.randomUUID().toString();

    log.error(
        String.format(
            "Exception(id: %s) while executing data fetcher for %s: %s",
            errorId, path, exception.getMessage()),
        exception);

    GraphQLError error;

    if (exception instanceof AccessDeniedException) {
      error = getGraphqlError(TypedGraphQLError.newPermissionDeniedBuilder(), path, errorId);
    } else if (exception instanceof DgsEntityNotFoundException) {
      error = getGraphqlError(TypedGraphQLError.newNotFoundBuilder(), path, errorId);
    } else if (exception instanceof DgsBadRequestException) {
      error = getGraphqlError(TypedGraphQLError.newBadRequestBuilder(), path, errorId);
    } else {
      error = getGraphqlError(TypedGraphQLError.newInternalErrorBuilder(), path, errorId);
    }

    return DataFetcherExceptionHandlerResult.newResult().error(error).build();
  }

  private GraphQLError getGraphqlError(Builder builder, ResultPath path, String errorId) {
    return builder.message("errorId: %s", errorId).path(path).build();
  }
}
