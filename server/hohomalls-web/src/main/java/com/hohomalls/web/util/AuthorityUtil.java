package com.hohomalls.web.util;

import com.hohomalls.core.common.Global;
import com.hohomalls.core.enumeration.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The interface of AuthorityUtil.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/7/2021
 */
public interface AuthorityUtil {

  @NotNull
  static List<GrantedAuthority> getAnonymousAuthority() {
    return AuthorityUtils.createAuthorityList(Role.ROLE_ANONYMOUS.name());
  }

  @NotNull
  static List<GrantedAuthority> getAuthorityList(@Nullable List<Role> roles) {
    if (CollectionUtils.isEmpty(roles)) {
      return List.of();
    }

    return AuthorityUtils.commaSeparatedStringToAuthorityList(
        roles.stream().map(Role::name).collect(Collectors.joining(Global.COMMA)));
  }
}
