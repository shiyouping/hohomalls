package com.hohomalls.core.util;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/** The utility for date and time. */
public interface DateTimeUtil {

  /** Get an OffsetDateTime based on UTC0. */
  @NotNull
  static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneId.of("+0"));
  }
}
