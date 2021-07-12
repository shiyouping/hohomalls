package com.hohomalls.web.security;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * The class of AuthorizationManager.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 12/7/2021
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

  @Override
  public Mono<AuthorizationDecision> check(
      Mono<Authentication> authentication, AuthorizationContext object) {
    // As long as the requests reach here, they should be authenticated.
    // The authorization will be checked on method level based on roles
    return authentication
        .filter(Authentication::isAuthenticated)
        .map(auth -> new AuthorizationDecision(true))
        .defaultIfEmpty(new AuthorizationDecision(false));
  }
}
