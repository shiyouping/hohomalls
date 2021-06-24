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
    var jwsToken = auth.getCredentials().toString();
    var emailOptional = this.tokenService.getEmail(jwsToken);
    var roleList = this.tokenService.getRoles(jwsToken);

    if (emailOptional.isEmpty() || roleList.isEmpty()) {
      throw new InvalidTokenException("No email or roles");
    }

    return Mono.just(emailOptional.get())
        .map(
            email ->
                new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    roleList.stream()
                        .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toList())));
  }
}
