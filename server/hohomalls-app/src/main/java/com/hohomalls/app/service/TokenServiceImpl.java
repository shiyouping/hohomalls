package com.hohomalls.app.service;

import com.hohomalls.app.property.TokenProperties;
import com.hohomalls.core.util.KeyUtil;
import com.hohomalls.core.util.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;
import java.util.Optional;

/**
 * The class of TokenServiceImpl.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private static final String email = "email";
  private static final String nickname = "nickname";
  private static Key privateKey;
  private static Key publicKey;
  private final TokenProperties properties;

  @Override
  public @NotNull Optional<String> generate(@Nullable String email, @Nullable String nickname) {
    if (email == null || nickname == null) {
      return Optional.empty();
    }

    if (privateKey == null) {
      privateKey = KeyUtil.toPrivateKey(this.properties.getPrivateKey());
    }

    Map<String, Object> claims =
        Map.of(TokenServiceImpl.email, email, TokenServiceImpl.nickname, nickname);
    return Optional.of(TokenUtil.generate(privateKey, claims, this.properties.getLifespan()));
  }

  @Override
  public @NotNull Optional<String> parseEmail(@Nullable String jws) {
    if (jws == null) {
      return Optional.empty();
    }

    if (publicKey == null) {
      publicKey = KeyUtil.toPublicKey(this.properties.getPublicKey());
    }

    Jws<Claims> claims = TokenUtil.parse(publicKey, jws, this.properties.getLifespan());
    return Optional.of(claims.getBody().get(TokenServiceImpl.email, String.class));
  }
}
