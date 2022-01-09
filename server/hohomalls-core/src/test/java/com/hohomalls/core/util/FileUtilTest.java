package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FileUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
public class FileUtilTest {

  @Test
  public void testReadAsBytes() {
    var actual = FileUtil.readAsBytes("image-file.png");
    assertThat(actual).isNotNull();
    assertThat(actual).isNotEmpty();
  }

  @Test
  public void testReadAsString() {
    assertThat(FileUtil.readAsString("text-file.txt")).isEqualTo("Hello World");
  }
}
