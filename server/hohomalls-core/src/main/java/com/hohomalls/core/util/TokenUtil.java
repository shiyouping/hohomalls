package com.hohomalls.core.util;

import com.hohomalls.core.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The interface of TokenUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public final class TokenUtil {

  private static final String subject = "access-token";
  private static final String issuer = "www.hohomalls.com";

  private TokenUtil() {}

  /** Generate a RSA JWS. */
  @NotNull
  public static String generate(
      @NotNull Key privateKey, @Nullable Map<String, Object> claims, @NotNull Long hours) {
    checkNotNull(privateKey, "privateKey cannot be null");
    checkNotNull(hours, "hours cannot be null");

    return Jwts.builder()
        .setSubject(subject)
        .setIssuer(issuer)
        .setId(UUID.randomUUID().toString())
        .setExpiration(Date.from(Instant.now().plus(hours, ChronoUnit.HOURS)))
        .addClaims(claims)
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
  }

  /** Parse and validate the JWS. */
  @NotNull
  public static Jws<Claims> parse(
      @NotNull Key publicKey, @NotNull String jws, @NotNull Long hours) {
    checkNotNull(publicKey, "publicKey cannot be null");
    checkNotNull(jws, "jws cannot be null");
    checkNotNull(hours, "hours cannot be null");

    Jws<Claims> claimsJws =
        Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jws);

    Claims body = claimsJws.getBody();
    if (!subject.equals(body.getSubject()) || !issuer.equals(body.getIssuer())) {
      throw new InvalidTokenException();
    }

    Date start = Date.from(Instant.now().minus(hours, ChronoUnit.HOURS));
    if (start.after(body.getExpiration())) {
      throw new InvalidTokenException();
    }

    return claimsJws;
  }
}
