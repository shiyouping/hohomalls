package com.hohomalls.app.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * The interface of TokenService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public interface TokenService {

  @NotNull
  Optional<String> generate(@Nullable String email, @Nullable String nickname);

  @NotNull
  Optional<String> parseEmail(@Nullable String jws);
}
