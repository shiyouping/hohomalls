package com.hohomalls.core.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

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
    var expiration = Date.from(Instant.parse("2050-01-01T00:00:00.00Z"));
    Map<String, Object> claims = Map.of("email", JwtUtilTest.email);

    assertThatIllegalArgumentException()
        .isThrownBy(() -> JwtUtil.generate(null, claims, expiration));

    assertThatNullPointerException()
        .isThrownBy(() -> JwtUtil.generate(JwtUtilTest.keyPair.getPrivate(), claims, null));

    JwtUtilTest.token = JwtUtil.generate(JwtUtilTest.keyPair.getPrivate(), claims, expiration);
    var jws =
        Jwts.parserBuilder()
            .setSigningKey(JwtUtilTest.keyPair.getPublic())
            .build()
            .parseClaimsJws(JwtUtilTest.token);
    assertThat(jws).as("jws").isNotNull();
    assertThat(jws.getHeader().getAlgorithm()).as("jws algorithm").isEqualTo("RS256");
    assertThat(jws.getBody().getIssuer()).as("jws issuer").isEqualTo("www.hohomalls.com");
    assertThat(jws.getBody().getId()).as("jws id").isNotNull();
    assertThat(jws.getBody().getExpiration()).as("jws expiration").isEqualTo(expiration);
    assertThat(jws.getBody().get("email")).as("jws email").isEqualTo(JwtUtilTest.email);
  }

  @Test
  @Order(2)
  public void testParse() {
    assertThatIllegalArgumentException()
        .as("IllegalArgumentException")
        .isThrownBy(() -> JwtUtil.parse(null, JwtUtilTest.token))
        .withMessageContaining("signing key cannot be null");

    assertThatIllegalArgumentException()
        .isThrownBy(() -> JwtUtil.parse(JwtUtilTest.keyPair.getPublic(), null))
        .withMessageContaining("JWT String argument cannot be null or empty.");

    var claims = JwtUtil.parse(JwtUtilTest.keyPair.getPublic(), JwtUtilTest.token);
    assertThat(claims).as("claims").isNotNull();
    assertThat(claims.getBody()).as("claims body").isNotNull();
    assertThat(claims.getBody().get("email")).as("claims email").isEqualTo(JwtUtilTest.email);
  }
}
