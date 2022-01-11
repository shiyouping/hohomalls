package com.hohomalls.core.exception;

import java.io.Serial;

/**
 * InvalidRequestException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/1/2022
 */
public class InvalidRequestException extends RuntimeException {

  @Serial private static final long serialVersionUID = 5857953584975387627L;

  public InvalidRequestException(String message) {
    super(message);
  }

  public InvalidRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidRequestException(Throwable cause) {
    super(cause);
  }

  protected InvalidRequestException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
