package com.hohomalls.core.util;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * FileUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public interface FileUtil {

  @SneakyThrows
  static byte[] readAsBytes(@NotNull String fileName) {
    var stream = FileUtil.readFrom(fileName);
    return Objects.requireNonNull(stream).readAllBytes();
  }

  @NotNull
  @SneakyThrows
  static String readAsString(@NotNull String fileName) {
    var stream = FileUtil.readFrom(fileName);

    return CharStreams.toString(
        new InputStreamReader(
            Objects.requireNonNull(stream, "stream cannot be null"), Charsets.UTF_8));
  }

  private static InputStream readFrom(String fileName) {
    return FileUtil.class.getClassLoader().getResourceAsStream(fileName);
  }
}
