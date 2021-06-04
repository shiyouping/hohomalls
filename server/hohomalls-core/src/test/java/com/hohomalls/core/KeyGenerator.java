package com.hohomalls.core;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * The class of KeyGenerator.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 4/6/2021
 */
public class KeyGenerator {
  @Test
  public void generate() {
    KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
    System.out.printf(
        "Private key: %s%n", Encoders.BASE64.encode(keyPair.getPrivate().getEncoded()));
    System.out.printf("Public key: %s%n", Encoders.BASE64.encode(keyPair.getPublic().getEncoded()));
  }
}
