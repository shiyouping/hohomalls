package com.hohomalls.web.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The class of AuthenticationFilter.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/7/2021
 */
@Component
public class AuthenticationFilter implements WebFilter {

  private final AuthenticationWebFilter authenticationWebFilter;

  @Autowired
  public AuthenticationFilter(
      @NotNull ReactiveAuthenticationManager authenticationManager,
      @NotNull ServerAuthenticationConverter authenticationConverter,
      @NotNull ServerAuthenticationEntryPoint authenticationEntryPoint) {

    checkNotNull(authenticationManager, "authenticationManager cannot be null");
    checkNotNull(authenticationConverter, "authenticationConverter cannot be null");
    checkNotNull(authenticationEntryPoint, "authenticationEntryPoint cannot be null");

    this.authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
    this.authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);
    this.authenticationWebFilter.setSecurityContextRepository(
        new WebSessionServerSecurityContextRepository());
    this.authenticationWebFilter.setAuthenticationFailureHandler(
        new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint));
  }

  @Override
  public @NotNull Mono<Void> filter(
      @NotNull ServerWebExchange exchange, @NotNull WebFilterChain chain) {
    return this.authenticationWebFilter.filter(exchange, chain);
  }
}
