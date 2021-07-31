package com.hohomalls.core.util;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.List;

/**
 * The interface of ArrayUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/7/2021
 */
public interface ArrayUtil {

  @Nullable
  static <T> T[] fromList(@Nullable List<T> list) {
    if (list == null || list.isEmpty()) {
      return null;
    }

    T[] array = (T[]) Array.newInstance(list.get(0).getClass(), list.size());
    return list.toArray(array);
  }

  static <T> boolean isEmpty(@Nullable T[] array) {
    return array == null || array.length == 0;
  }
}
