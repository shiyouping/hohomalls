package com.hohomalls.core.exception;

/**
 * The class of InvalidRequestException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 4/6/2021
 */
public class InvalidRequestException extends RuntimeException {
  private static final long serialVersionUID = 5857953584975387627L;

  public InvalidRequestException() {
    super();
  }

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
