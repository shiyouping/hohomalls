package com.hohomalls.web.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Check if the Authentication parsed from JwtTokenConverter is still valid.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 12/7/2021
 */
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    // TODO Look it up in the Redis cache
    return Mono.just(authentication);
  }
}
