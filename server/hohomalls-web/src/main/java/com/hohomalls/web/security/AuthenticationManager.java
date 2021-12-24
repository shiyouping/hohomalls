package com.hohomalls.web.security;

import com.hohomalls.web.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Check if the Authentication parsed from AuthenticationConverter is still valid.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 12/7/2021
 */
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final SessionService sessionService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    if (authentication instanceof AnonymousAuthenticationToken) {
      return Mono.just(authentication);
    }

    return sessionService
        .has(authentication.getCredentials().toString())
        .filter(hasSession -> hasSession)
        .switchIfEmpty(Mono.error(new BadCredentialsException("Session expired.")))
        .flatMap(hasSession -> Mono.just(authentication));
  }
}
