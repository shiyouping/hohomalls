package com.hohomalls.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.hohomalls.core.constant.ConfigProfiles.LOCAL;
import static com.hohomalls.core.constant.ConfigProfiles.PROD;
import static com.hohomalls.web.common.Role.ROLE_VISITOR;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The class of SecurityConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 8/6/2021
 */
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

  /** The security configurations for local env. */
  @Bean
  @Profile(LOCAL)
  public SecurityWebFilterChain nonProductionFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .anyExchange()
        .authenticated()
        .and()
        // Disable HTTP Security Response Headers. See details at:
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-headers
        .headers()
        .disable()
        // Disable CSRF Protection. See details at
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-csrf
        .csrf()
        .disable()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .build();
  }

  /** The security configurations for production env. */
  @Bean
  @Profile(PROD)
  public SecurityWebFilterChain productionFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange()
        .anyExchange()
        .authenticated()
        .and()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .redirectToHttps(withDefaults())
        .build();
  }

  /** Customize where to get the user information. */
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user =
        User.withUsername("user").password("password").roles(ROLE_VISITOR.name()).build();
    return new MapReactiveUserDetailsService(user);
  }
}
