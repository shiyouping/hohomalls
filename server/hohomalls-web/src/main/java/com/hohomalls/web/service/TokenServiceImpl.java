package com.hohomalls.web.service;

import com.hohomalls.core.constant.Global;
import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.core.util.JwtUtil;
import com.hohomalls.core.util.KeyUtil;
import com.hohomalls.web.common.Role;
import com.hohomalls.web.property.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static com.hohomalls.core.constant.Global.*;

/**
 * The class of TokenServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private static final String SUBJECT = "access-token";
  private static Key privateKey;
  private static Key publicKey;
  private final TokenProperties properties;

  @Override
  public @NotNull Optional<String> getEmail(@Nullable String token) {
    var claims = getJwsClaims(token);
    if (claims.isEmpty()) {
      return Optional.empty();
    }

    var email = claims.get().getBody().get(EMAIL, String.class);
    if (!EmailValidator.getInstance().isValid(email)) {
      throw new InvalidTokenException("Invalid email");
    }

    return Optional.of(email);
  }

  @Override
  public @NotNull List<Role> getRoles(@Nullable String token) {
    var claims = getJwsClaims(token);
    if (claims.isEmpty()) {
      return List.of();
    }

    var roles = claims.get().getBody().get(ROLES, String.class);
    if (!StringUtils.hasText(roles)) {
      throw new InvalidTokenException("Invalid role");
    }

    return Arrays.stream(roles.split(COMMA)).map(Role::valueOf).collect(Collectors.toList());
  }

  @Override
  public @NotNull Optional<String> getToken(
      @Nullable String email, @Nullable String nickname, @Nullable Role... roles) {
    if (email == null || nickname == null || roles == null || roles.length == 0) {
      return Optional.empty();
    }

    if (privateKey == null) {
      privateKey = KeyUtil.toPrivateKey(this.properties.getPrivateKey());
    }

    var roleList =
        Arrays.stream(roles).filter(Objects::nonNull).map(Enum::name).collect(Collectors.toList());
    var roleString = String.join(COMMA, roleList);
    var expiration = Date.from(Instant.now().plus(this.properties.getLifespan(), ChronoUnit.HOURS));
    Map<String, Object> claims =
        Map.of(Global.SUBJECT, SUBJECT, EMAIL, email, NICKNAME, nickname, ROLES, roleString);
    return Optional.of(JwtUtil.generate(privateKey, claims, expiration));
  }

  private Optional<Jws<Claims>> getJwsClaims(@Nullable String token) {
    if (token == null) {
      return Optional.empty();
    }

    if (publicKey == null) {
      publicKey = KeyUtil.toPublicKey(this.properties.getPublicKey());
    }

    var claims = JwtUtil.parse(publicKey, token);
    var body = claims.getBody();
    if (!SUBJECT.equalsIgnoreCase(body.getSubject())) {
      throw new InvalidTokenException("Invalid subject");
    }

    var start = Date.from(Instant.now().minus(this.properties.getLifespan(), ChronoUnit.HOURS));
    if (start.after(body.getExpiration())) {
      throw new InvalidTokenException("Token expired");
    }

    return Optional.of(claims);
  }
}
