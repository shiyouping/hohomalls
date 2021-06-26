package com.hohomalls.web.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Get the token and forward to AuthenticationManager.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 26/6/2021
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

  private static final String AUTH_PREFIX = "Bearer";
  private final AuthenticationManager authenticationManager;

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(AUTHORIZATION))
        .filter(header -> header.startsWith(AUTH_PREFIX))
        .flatMap(
            authHeader -> {
              var token = authHeader.substring(AUTH_PREFIX.length()).trim();
              var auth = new UsernamePasswordAuthenticationToken(token, token);
              return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
            });
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
