package com.hohomalls.core.util;

import org.jetbrains.annotations.Nullable;
import org.passay.*;

/**
 * PasswordUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public interface PasswordUtil {

  static boolean isValid(@Nullable String password) {
    if (password == null) {
      return false;
    }

    var data = new PasswordData(password);
    var lengthRule = new LengthRule(8, 32);
    var whitespaceRule = new WhitespaceRule();
    var characterCharacteristicsRule =
        new CharacterCharacteristicsRule(
            2,
            new CharacterRule(EnglishCharacterData.LowerCase),
            new CharacterRule(EnglishCharacterData.UpperCase),
            new CharacterRule(EnglishCharacterData.Digit),
            new CharacterRule(EnglishCharacterData.Special));

    var validator = new PasswordValidator(lengthRule, whitespaceRule, characterCharacteristicsRule);
    return validator.validate(data).isValid();
  }
}
