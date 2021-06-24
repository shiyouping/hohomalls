package com.hohomalls.web.security;

import com.hohomalls.web.common.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class of UserDetails.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/6/2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  private static final long serialVersionUID = -2223848817495651981L;

  private String username;
  private String password;
  private boolean enabled;
  private List<Role> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.name()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
