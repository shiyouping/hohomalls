package com.hohomalls.web.config;

import com.hohomalls.web.common.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.config.web.server.ServerHttpSecurity.HeaderSpec;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static com.hohomalls.core.constant.ConfigProfile.LOCAL;
import static com.hohomalls.core.constant.ConfigProfile.PROD;
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
    // Disable HTTP Security Response Headers. See details at:
    // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-headers
    return http.headers(HeaderSpec::disable)
        // Disable CSRF Protection. See details at
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#webflux-csrf
        .csrf(CsrfSpec::disable)
        .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
        .build();
  }

  /** The security configurations for production env. */
  @Bean
  @Profile(PROD)
  public SecurityWebFilterChain productionFilterChain(ServerHttpSecurity http) {
    return http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
        .redirectToHttps(withDefaults())
        .build();
  }

  /** Customize where to get the user information. */
  @Bean
  public MapReactiveUserDetailsService userDetailsService() {
    UserDetails user =
        User.withUsername("user").password("password").roles(Role.VISITOR.name()).build();
    return new MapReactiveUserDetailsService(user);
  }
}
