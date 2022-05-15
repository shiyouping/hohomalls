package com.hohomalls.core.util;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;

/**
 * StringUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 15/5/2022
 */
public interface StringUtil {

  @NotNull
  static String getNumber(long value) {
    return Strings.padStart(String.valueOf(value), 19, '0');
  }
}
