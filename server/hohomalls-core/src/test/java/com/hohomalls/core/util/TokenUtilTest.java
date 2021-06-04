package com.hohomalls.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class of TokenUtilTest.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public class TokenUtilTest {

  private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
  private static final String email = "ricky@gmail.com";
  private static final long hours = 24L;
  private static String jwsToken;

  @Test
  @Order(1)
  public void testGenerate() {
    Map<String, Object> claims = Map.of("email", email);
    jwsToken = TokenUtil.generate(keyPair.getPrivate(), claims, hours);
    Jws<Claims> jws =
        Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(jwsToken);

    assertThat(jws).as("check jws").isNotNull();
    assertThat(jws.getHeader().getAlgorithm()).as("check jws algorithm").isEqualTo("RS256");
    assertThat(jws.getBody().getSubject()).as("check jws subject").isEqualTo("access-token");
    assertThat(jws.getBody().getIssuer()).as("check jws issuer").isEqualTo("www.hohomalls.com");
    assertThat(jws.getBody().getId()).as("check jws id").isNotNull();
    assertThat(jws.getBody().getExpiration()).as("check jws expiration").isAfter(new Date());
    assertThat(jws.getBody().get("email")).as("check jws email").isEqualTo(email);
  }

  @Test
  @Order(2)
  public void testParse() {
    Jws<Claims> claims = TokenUtil.parse(keyPair.getPublic(), jwsToken, hours);
    assertThat(claims).as("check claims").isNotNull();
    assertThat(claims.getBody()).as("check claims body").isNotNull();
    assertThat(claims.getBody().get("email")).as("check claims email").isEqualTo(email);
  }
}
