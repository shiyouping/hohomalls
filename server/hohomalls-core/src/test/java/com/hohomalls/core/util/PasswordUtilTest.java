package com.hohomalls.core.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PasswordUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public class PasswordUtilTest {

  @Test
  public void testIsValid() {
    assertThat(PasswordUtil.isValid("12345abcde")).as("Valid password").isTrue();
    assertThat(PasswordUtil.isValid("12345abcdeABCDE")).as("Valid invalid").isTrue();
    assertThat(PasswordUtil.isValid("12345abcde!")).as("Valid invalid").isTrue();

    assertThat(PasswordUtil.isValid("1234")).as("Password too short").isFalse();
    assertThat(PasswordUtil.isValid("12345 abcdeABCDE")).as("Password has whitespace").isFalse();
    assertThat(PasswordUtil.isValid("12345abcdeABCDE!12345abcdeABCDE!12345abcdeABCDE!"))
        .as("Password too long")
        .isFalse();
  }
}
