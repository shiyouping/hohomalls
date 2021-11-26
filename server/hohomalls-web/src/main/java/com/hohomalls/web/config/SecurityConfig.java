package com.hohomalls.web.config;

import com.hohomalls.web.filter.AuthenticationFilter;
import com.hohomalls.web.security.AuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.hohomalls.core.common.Global.PROFILE_NON_PROD;
import static com.hohomalls.core.common.Global.PROFILE_PROD;
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
// TODO Use Spring security to protect APIs
// @EnableReactiveMethodSecurity
public class SecurityConfig {

  private final AuthenticationFilter authenticationFilter;
  private final AuthorizationManager authorizationManager;

  /** The security configurations for local env. */
  @Bean
  @Profile(PROFILE_NON_PROD)
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
  @Profile(PROFILE_PROD)
  public SecurityWebFilterChain productionFilterChain(ServerHttpSecurity http) {
    return commonSecurity(http).redirectToHttps(withDefaults()).build();
  }

  private ServerHttpSecurity commonSecurity(ServerHttpSecurity http) {
    return http.formLogin()
        .disable()
        .httpBasic()
        .disable()
        .logout()
        .disable()
        .authorizeExchange()
        .anyExchange()
        .access(this.authorizationManager)
        .and()
        .addFilterAt(this.authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);
  }
}
