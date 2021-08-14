package com.hohomalls.core.exception;

/**
 * The class of KeyException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 14/8/2021
 */
public class KeyException extends RuntimeException {
  private static final long serialVersionUID = 5783930684555858723L;

  public KeyException() {}

  public KeyException(String message) {
    super(message);
  }

  public KeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public KeyException(Throwable cause) {
    super(cause);
  }

  public KeyException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
