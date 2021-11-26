package com.hohomalls.core.exception;

/**
 * The class of InternalServerException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/7/2021
 */
public class InternalServerException extends RuntimeException {
  private static final long serialVersionUID = 557581850064263066L;

  public InternalServerException() {
    super();
  }

  public InternalServerException(String message) {
    super(message);
  }

  public InternalServerException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerException(Throwable cause) {
    super(cause);
  }

  protected InternalServerException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
