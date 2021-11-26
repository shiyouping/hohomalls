package com.hohomalls.core.util;

import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/** The utility for date and time. */
public interface DateTimeUtil {

  /** Get an OffsetDateTime based on UTC-0. */
  @NotNull
  static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneId.of("+0"));
  }

  /** Get present date and time in String, e.g. 2021-07-19T16:24:28.632773Z . */
  @NotNull
  static String present() {
    return DateTimeFormatter.ISO_DATE_TIME.format(now());
  }
}
