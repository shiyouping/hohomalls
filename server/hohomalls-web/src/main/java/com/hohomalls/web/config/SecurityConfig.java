package com.hohomalls.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.hohomalls.core.constant.ConfigProfiles.LOCAL;
import static com.hohomalls.core.constant.ConfigProfiles.PROD;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The class of SecurityConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 8/6/2021
 */
@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  private final ReactiveAuthenticationManager authenticationManager;
  private final ServerSecurityContextRepository securityContextRepository;

  /** The security configurations for local env. */
  @Bean
  @Profile(LOCAL)
  public SecurityWebFilterChain localFilterChain(ServerHttpSecurity http) {
    return commonSecurity(http)
        // Disable HTTP Security Response Headers. See details at:
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-headers
        .headers()
        .disable()
        // Disable CSRF Protection. See details at
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-csrf
        .csrf()
        .disable()
        .build();
  }

  /** The security configurations for production env. */
  @Bean
  @Profile(PROD)
  public SecurityWebFilterChain productionFilterChain(ServerHttpSecurity http) {
    return commonSecurity(http).redirectToHttps(withDefaults()).build();
  }

  private ServerHttpSecurity commonSecurity(ServerHttpSecurity http) {
    return http.exceptionHandling()
        .authenticationEntryPoint(this::entryPoint)
        .accessDeniedHandler(this::handler)
        .and()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .authenticationManager(this.authenticationManager)
        .securityContextRepository(this.securityContextRepository)
        .authorizeExchange()
        .anyExchange()
        .authenticated()
        .and();
  }

  private Mono<Void> entryPoint(ServerWebExchange exchange, AuthenticationException ex) {
    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
  }

  private Mono<Void> handler(ServerWebExchange exchange, AccessDeniedException denied) {
    return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
  }
}
