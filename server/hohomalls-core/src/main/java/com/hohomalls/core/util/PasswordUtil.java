package com.hohomalls.core.util;

import org.jetbrains.annotations.Nullable;
import org.passay.*;

import java.util.regex.Pattern;

/**
 * PasswordUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/12/2021
 */
public interface PasswordUtil {

  /**
   * See the reference at {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}.
   */
  Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

  /** Check if the password is already encoded by bcrypt. */
  static boolean isEncoded(@Nullable String password) {
    if (password == null) {
      return false;
    }

    return PasswordUtil.BCRYPT_PATTERN.matcher(password).matches();
  }

  /** Check if the raw password before encoding complies with the given rules. */
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
