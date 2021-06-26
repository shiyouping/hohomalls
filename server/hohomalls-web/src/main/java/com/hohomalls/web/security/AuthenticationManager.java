package com.hohomalls.web.security;

import com.hohomalls.core.exception.InvalidTokenException;
import com.hohomalls.web.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

/**
 * The class of AuthenticationManager.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/6/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final TokenService tokenService;

  @Override
  public Mono<Authentication> authenticate(Authentication auth) {
    var token = auth.getCredentials().toString();
    var emailOptional = this.tokenService.getEmail(token);
    var roles = this.tokenService.getRoles(token);

    return Mono.justOrEmpty(emailOptional)
        .map(
            email -> {
              if (roles.isEmpty()) {
                throw new InvalidTokenException("No roles found");
              }

              return new UsernamePasswordAuthenticationToken(
                  email,
                  null,
                  roles.stream()
                      .map(role -> new SimpleGrantedAuthority(role.name()))
                      .collect(Collectors.toList()));
            });
  }
}
