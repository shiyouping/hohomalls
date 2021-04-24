package com.hohomalls.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Provide utilities to String.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public interface StringUtil {

  @NonNull
  static String getUniqueId() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
