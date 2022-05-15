package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTest {

  @Test
  public void testGetNumber() {
    assertThat(StringUtil.getNumber(1)).isEqualTo("0000000000000000001");
    assertThat(StringUtil.getNumber(10)).isEqualTo("0000000000000000010");
    assertThat(StringUtil.getNumber(123456789)).isEqualTo("0000000000123456789");
  }
}
