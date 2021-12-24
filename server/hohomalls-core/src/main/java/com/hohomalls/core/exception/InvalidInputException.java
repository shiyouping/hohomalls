package com.hohomalls.core.exception;

import java.io.Serial;

/**
 * InvalidInputException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/12/2021
 */
public class InvalidInputException extends RuntimeException {

  @Serial private static final long serialVersionUID = 6792078452531817778L;

  public InvalidInputException(String message) {
    super(message);
  }

  public InvalidInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidInputException(Throwable cause) {
    super(cause);
  }

  protected InvalidInputException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
