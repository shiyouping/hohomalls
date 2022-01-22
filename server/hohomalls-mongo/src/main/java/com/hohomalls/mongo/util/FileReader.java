package com.hohomalls.mongo.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.util.Objects;

/**
 * FileReader.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 2/1/2022
 */
public interface FileReader {

  @NotNull
  @SneakyThrows
  static String read(@NotNull String fileName) {
    var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

    return CharStreams.toString(
        new InputStreamReader(
            Objects.requireNonNull(stream, "stream cannot be null"), Charsets.UTF_8));
  }
}
