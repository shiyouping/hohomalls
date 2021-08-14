package com.hohomalls.core.util;

import com.hohomalls.core.exception.KeyException;
import io.jsonwebtoken.io.Decoders;
import org.jetbrains.annotations.NotNull;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The interface of KeyUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 4/6/2021
 */
public interface KeyUtil {

  /** Get the private key from its encoded string. */
  @NotNull
  static Key toPrivateKey(@NotNull String keyString) {
    checkNotNull(keyString, "keyString cannot be null");

    try {
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Decoders.BASE64.decode(keyString));
      KeyFactory factory = KeyFactory.getInstance("RSA");
      return factory.generatePrivate(keySpec);
    } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
      throw new KeyException("Failed to get the private key", ex);
    }
  }

  /** Get the public key from its encoded string. */
  @NotNull
  static Key toPublicKey(@NotNull String keyString) {
    checkNotNull(keyString, "keyString cannot be null");

    try {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Decoders.BASE64.decode(keyString));
      KeyFactory factory = KeyFactory.getInstance("RSA");
      return factory.generatePublic(keySpec);
    } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
      throw new KeyException("Failed to get the public key", ex);
    }
  }
}
