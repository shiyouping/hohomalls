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

  @SneakyThrows
  static String read(@NotNull String fileName) {
    var stream =
        FileReader.class
            .getClassLoader()
            .getResourceAsStream(Objects.requireNonNull(fileName, "fileName cannot be null"));

    return CharStreams.toString(
        new InputStreamReader(
            Objects.requireNonNull(stream, "stream cannot be null"), Charsets.UTF_8));
  }
}
