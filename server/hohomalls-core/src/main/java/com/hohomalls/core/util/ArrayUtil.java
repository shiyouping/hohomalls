package com.hohomalls.core.util;

/**
 * The interface of ArrayUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/7/2021
 */
public interface ArrayUtil {

  static <T> boolean isEmpty(T[] array) {
    return array == null || array.length == 0;
  }
}
