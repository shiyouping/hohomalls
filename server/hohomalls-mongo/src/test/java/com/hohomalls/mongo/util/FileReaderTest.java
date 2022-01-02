package com.hohomalls.mongo.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReaderTest {

  @Test
  public void testRead() {
    var actual = FileReader.read("FileReaderTest.txt");
    assertThat(actual).isEqualTo("Hello World");
  }
}
