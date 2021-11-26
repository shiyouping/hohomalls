package com.hohomalls.web.service;

import com.hohomalls.web.common.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * The interface of TokenService.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
public interface TokenService {

  @NotNull
  Optional<String> getEmail(@Nullable String jws);

  @NotNull
  List<Role> getRoles(@Nullable String jws);

  @NotNull
  Optional<String> getToken(
      @Nullable String email, @Nullable String nickname, @Nullable Role... roles);

  @NotNull
  Optional<String> getToken(
      @Nullable String email, @Nullable String nickname, @Nullable List<Role> roles);
}
