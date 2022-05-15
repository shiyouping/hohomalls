package com.hohomalls.data.util;

import com.hohomalls.data.pojo.BaseDoc;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

/**
 * DocUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/1/2022
 */
public interface DocUtil {

  static void updateDateTime(@Nullable BaseDoc doc) {
    if (doc == null) {
      return;
    }

    Instant now = Instant.now();

    if (doc.getCreatedAt() == null) {
      doc.setCreatedAt(now);
    }

    doc.setUpdatedAt(now);
  }
}
