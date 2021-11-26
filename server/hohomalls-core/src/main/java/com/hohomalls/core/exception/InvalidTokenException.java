package com.hohomalls.core.exception;

/**
 * The class of InvalidTokenException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public class InvalidTokenException extends RuntimeException {
  private static final long serialVersionUID = -8544042869950326470L;

  public InvalidTokenException() {
    super();
  }

  public InvalidTokenException(String message) {
    super(message);
  }

  public InvalidTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidTokenException(Throwable cause) {
    super(cause);
  }

  protected InvalidTokenException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
