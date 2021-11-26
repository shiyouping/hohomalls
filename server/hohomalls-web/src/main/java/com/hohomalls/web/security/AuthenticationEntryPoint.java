package com.hohomalls.web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Handles AuthenticationException.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/7/2021
 */
@Slf4j
@Component
public class AuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

  @Override
  public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException exception) {
    log.error("Authentication failed", exception);
    // Throw the exception to let WebExceptionHandler handle it,
    // So that all the exceptions' attributes will be
    // defined in GlobalExceptionAttributes class
    throw exception;
  }
}
