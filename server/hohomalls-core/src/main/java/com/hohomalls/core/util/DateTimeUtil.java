package com.hohomalls.core.util;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/** The utility for date and time. */
public interface DateTimeUtil {

  static OffsetDateTime now() {
    return OffsetDateTime.now(ZoneId.of("+0"));
  }
}
