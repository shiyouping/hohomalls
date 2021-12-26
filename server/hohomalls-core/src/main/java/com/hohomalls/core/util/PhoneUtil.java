package com.hohomalls.core.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

/**
 * PhoneUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public interface PhoneUtil {

  @SneakyThrows
  static boolean isValid(@Nullable String number) {
    if (number == null || !number.startsWith("+")) {
      return false;
    }

    var instance = PhoneNumberUtil.getInstance();
    var phoneNumber = instance.parse(number, "HK");
    return instance.isValidNumber(phoneNumber);
  }
}
