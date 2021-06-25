package com.hohomalls.core.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * The class of JwtUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JwtUtilTest {

  private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
  private static final String email = "ricky@gmail.com";
  private static String token;

  @Test
  @Order(1)
  public void testGenerate() {
    var expiration = new Date(System.currentTimeMillis() + 10000);
    Map<String, Object> claims = Map.of("email", email);

    assertThatNullPointerException()
        .as("NullPointerException")
        .isThrownBy(() -> JwtUtil.generate(null, claims, expiration))
        .withMessageContaining("privateKey cannot be null");

    assertThatNullPointerException()
        .as("NullPointerException")
        .isThrownBy(() -> JwtUtil.generate(keyPair.getPrivate(), claims, null))
        .withMessageContaining("expiration cannot be null");

    token = JwtUtil.generate(keyPair.getPrivate(), claims, expiration);
    var jws = Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(token);
    assertThat(jws).as("jws").isNotNull();
    assertThat(jws.getHeader().getAlgorithm()).as("jws algorithm").isEqualTo("RS256");
    assertThat(jws.getBody().getIssuer()).as("jws issuer").isEqualTo("www.hohomalls.com");
    assertThat(jws.getBody().getId()).as("jws id").isNotNull();
    assertThat(jws.getBody().getExpiration().getSeconds())
        .as("jws expiration")
        .isEqualTo(expiration.getSeconds());
    assertThat(jws.getBody().get("email")).as("jws email").isEqualTo(email);
  }

  @Test
  @Order(2)
  public void testParse() {
    assertThatNullPointerException()
        .as("NullPointerException")
        .isThrownBy(() -> JwtUtil.parse(null, token))
        .withMessageContaining("publicKey cannot be null");

    assertThatNullPointerException()
        .as("NullPointerException")
        .isThrownBy(() -> JwtUtil.parse(keyPair.getPublic(), null))
        .withMessageContaining("jws cannot be null");

    var claims = JwtUtil.parse(keyPair.getPublic(), token);
    assertThat(claims).as("claims").isNotNull();
    assertThat(claims.getBody()).as("claims body").isNotNull();
    assertThat(claims.getBody().get("email")).as("claims email").isEqualTo(email);
  }
}
