package com.hohomalls.core.util;

import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;

/**
 * HashUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public interface HashUtil {

  /** Murmur3 is good at uniqueness, speed and consistent hashing. */
  @NotNull
  static String getMurmur3(byte[] data) {
    return Hashing.murmur3_128().hashBytes(data).toString();
  }
}
