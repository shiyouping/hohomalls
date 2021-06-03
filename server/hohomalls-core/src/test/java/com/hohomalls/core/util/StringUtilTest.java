package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
public class StringUtilTest {

  @Test
  public void testGetUniqueId() {
    assertThat(StringUtil.getUniqueId()).as("check unique id").doesNotContain("-");
  }
}
