package com.hohomalls.core.util;

import com.hohomalls.core.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The interface of JwtUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public final class JwtUtil {

  private static final String issuer = "www.hohomalls.com";

  private JwtUtil() {}

  /** Generate a RSA JWS. */
  @NotNull
  public static String generate(
      @NotNull Key privateKey, @Nullable Map<String, Object> claims, @NotNull Date expiration) {
    checkNotNull(privateKey, "privateKey cannot be null");
    checkNotNull(expiration, "expiration cannot be null");

    return Jwts.builder()
        .setIssuer(issuer)
        .setId(UUID.randomUUID().toString())
        .setExpiration(expiration)
        .addClaims(claims)
        .signWith(privateKey, SignatureAlgorithm.RS256)
        .compact();
  }

  /** Parse and validate the JWS. */
  @NotNull
  public static Jws<Claims> parse(@NotNull Key publicKey, @NotNull String jws) {
    checkNotNull(publicKey, "publicKey cannot be null");
    checkNotNull(jws, "jws cannot be null");

    var claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jws);
    if (claims == null) {
      throw new InvalidTokenException();
    }

    var body = claims.getBody();
    if (!issuer.equals(body.getIssuer())) {
      throw new InvalidTokenException("Invalid issuer");
    }

    return claims;
  }
}
