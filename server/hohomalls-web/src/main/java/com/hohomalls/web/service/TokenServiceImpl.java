package com.hohomalls.web.service;

import com.hohomalls.core.util.KeyUtil;
import com.hohomalls.core.util.TokenUtil;
import com.hohomalls.web.common.Role;
import com.hohomalls.web.property.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static com.hohomalls.core.constant.Common.*;

/**
 * The class of TokenServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
  private static Key privateKey;
  private static Key publicKey;
  private final TokenProperties properties;

  @Override
  public @NotNull Optional<String> generate(
      @Nullable String email, @Nullable String nickname, @Nullable Role... roles) {
    if (email == null || nickname == null || roles == null || roles.length == 0) {
      return Optional.empty();
    }

    if (privateKey == null) {
      privateKey = KeyUtil.toPrivateKey(this.properties.getPrivateKey());
    }

    var roleList =
        Arrays.stream(roles).filter(Objects::nonNull).map(Enum::name).collect(Collectors.toList());
    var finalRoles = String.join(DELIMITER, roleList);

    Map<String, Object> claims = Map.of(EMAIL, email, NICKNAME, nickname, ROLES, finalRoles);
    return Optional.of(TokenUtil.generate(privateKey, claims, this.properties.getLifespan()));
  }

  @Override
  public @NotNull Optional<String> getEmail(@Nullable String jws) {
    var claims = getJwsClaims(jws);
    if (claims.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(claims.get().getBody().get(EMAIL, String.class));
  }

  @Override
  public @NotNull List<Role> getRoles(@Nullable String jws) {
    var claims = getJwsClaims(jws);
    if (claims.isEmpty()) {
      return List.of();
    }

    var roles = claims.get().getBody().get(ROLES, String.class);
    return Arrays.stream(roles.split(DELIMITER)).map(Role::valueOf).collect(Collectors.toList());
  }

  private Optional<Jws<Claims>> getJwsClaims(@Nullable String jws) {
    if (jws == null) {
      return Optional.empty();
    }

    if (publicKey == null) {
      publicKey = KeyUtil.toPublicKey(this.properties.getPublicKey());
    }

    return Optional.of(TokenUtil.parse(publicKey, jws, this.properties.getLifespan()));
  }
}
