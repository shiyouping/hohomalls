package com.hohomalls.core.util;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Provide utilities to String.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface StringUtil {

  @NotNull
  static String getUniqueId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
