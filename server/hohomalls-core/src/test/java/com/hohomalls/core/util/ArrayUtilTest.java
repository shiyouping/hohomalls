package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class of ArrayUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 31/7/2021
 */
public class ArrayUtilTest {

  @Test
  public void testFromList() {
    assertThat(ArrayUtil.fromList(null) == null).isTrue();
    assertThat(ArrayUtil.fromList(List.of("S"))).isEqualTo(new String[] {"S"});
  }

  @Test
  public void testIsEmpty() {
    assertThat(ArrayUtil.isEmpty(null)).isTrue();
    assertThat(ArrayUtil.isEmpty(new String[0])).isTrue();
    assertThat(ArrayUtil.isEmpty(new String[] {"ssss"})).isFalse();
  }
}
