package com.hohomalls.web.service;

import com.hohomalls.core.common.Global;
import com.hohomalls.core.enumeration.Role;
import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.core.util.ArrayUtil;
import com.hohomalls.core.util.JwtUtil;
import com.hohomalls.core.util.KeyUtil;
import com.hohomalls.web.config.WebProperties;
import com.hohomalls.web.util.HttpHeaderUtil;
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

import static com.hohomalls.core.common.Global.*;

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
  private final WebProperties webProperties;

  @Override
  public @NotNull Optional<String> getEmailFromAuth(@Nullable String auth) {
    if (auth == null) {
      return Optional.empty();
    }

    var token = HttpHeaderUtil.getAuth(auth);
    return this.getEmailFromJwt(token.orElse(null));
  }

  @Override
  public @NotNull Optional<String> getEmailFromJwt(@Nullable String jwt) {
    var claims = this.getJwsClaims(jwt);
    if (claims.isEmpty()) {
      return Optional.empty();
    }

    var email = claims.get().getBody().get(JWT_EMAIL, String.class);
    if (!EmailValidator.getInstance().isValid(email)) {
      throw new InvalidTokenException("Invalid email");
    }

    return Optional.of(email);
  }

  @Override
  public @NotNull List<Role> getRoles(@Nullable String jwt) {
    var claims = this.getJwsClaims(jwt);
    if (claims.isEmpty()) {
      return List.of();
    }

    var roles = claims.get().getBody().get(JWT_ROLES, String.class);
    if (!StringUtils.hasText(roles)) {
      throw new InvalidTokenException("Invalid role");
    }

    return Arrays.stream(roles.split(SIGN_COMMA)).map(Role::valueOf).collect(Collectors.toList());
  }

  @Override
  public @NotNull Optional<String> getToken(
      @Nullable String email, @Nullable String nickname, @Nullable Role... roles) {
    if (email == null || nickname == null || roles == null || roles.length == 0) {
      return Optional.empty();
    }

    if (TokenServiceImpl.privateKey == null) {
      TokenServiceImpl.privateKey = KeyUtil.toPrivateKey(this.webProperties.token().privateKey());
    }

    var roleList =
        Arrays.stream(roles).filter(Objects::nonNull).map(Enum::name).collect(Collectors.toList());
    var roleString = String.join(SIGN_COMMA, roleList);
    var expiration =
        Date.from(Instant.now().plus(this.webProperties.token().lifespan(), ChronoUnit.HOURS));
    Map<String, Object> claims =
        Map.of(
            Global.JWT_SUBJECT,
            TokenServiceImpl.SUBJECT,
            JWT_EMAIL,
            email,
            JWT_NICKNAME,
            nickname,
            JWT_ROLES,
            roleString);
    return Optional.of(JwtUtil.generate(TokenServiceImpl.privateKey, claims, expiration));
  }

  @Override
  public @NotNull Optional<String> getToken(
      @Nullable String email, @Nullable String nickname, @Nullable List<Role> roles) {
    return this.getToken(email, nickname, ArrayUtil.fromList(roles));
  }

  private Optional<Jws<Claims>> getJwsClaims(@Nullable String token) {
    if (token == null) {
      return Optional.empty();
    }

    if (TokenServiceImpl.publicKey == null) {
      TokenServiceImpl.publicKey = KeyUtil.toPublicKey(this.webProperties.token().publicKey());
    }

    var claims = JwtUtil.parse(TokenServiceImpl.publicKey, token);
    var body = claims.getBody();
    if (!TokenServiceImpl.SUBJECT.equalsIgnoreCase(body.getSubject())) {
      throw new InvalidTokenException("Invalid subject");
    }

    var start =
        Date.from(Instant.now().minus(this.webProperties.token().lifespan(), ChronoUnit.HOURS));
    if (start.after(body.getExpiration())) {
      throw new InvalidTokenException("Token expired");
    }

    return Optional.of(claims);
  }
}
