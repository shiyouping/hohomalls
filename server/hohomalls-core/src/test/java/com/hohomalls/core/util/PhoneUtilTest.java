package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PhoneUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public class PhoneUtilTest {

  @Test
  public void testIsValid() {
    assertThat(PhoneUtil.isValid("12345")).as("Invalid phone number").isFalse();
    assertThat(PhoneUtil.isValid("63238753")).as("Valid phone number").isFalse();
    assertThat(PhoneUtil.isValid("+85263238753")).as("Valid phone number").isTrue();
    assertThat(PhoneUtil.isValid("+8618606060600")).as("Valid phone number").isTrue();
  }
}
