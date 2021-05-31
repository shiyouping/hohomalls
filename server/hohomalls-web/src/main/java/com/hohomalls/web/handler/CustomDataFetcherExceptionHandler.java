package com.hohomalls.web.handler;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

/**
 * The class of CustomDataFetcherExceptionHandler.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 31/5/2021
 */
@Component
public class CustomDataFetcherExceptionHandler implements DataFetcherExceptionHandler {
  private final DefaultDataFetcherExceptionHandler defaultHandler =
      new DefaultDataFetcherExceptionHandler();

  @Override
  public DataFetcherExceptionHandlerResult onException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    // TODO implement the error handler here
    return this.defaultHandler.onException(handlerParameters);
  }
}
