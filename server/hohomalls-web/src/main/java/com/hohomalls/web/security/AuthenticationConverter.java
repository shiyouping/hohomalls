package com.hohomalls.web.security;

import com.hohomalls.web.common.Role;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.util.AuthorityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * Converts JWT token to an Authentication.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 11/7/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationConverter implements ServerAuthenticationConverter {

  private static final String AUTH_PREFIX = "Bearer";
  private static final String ANONYMOUS = "ANONYMOUS";

  private final TokenService tokenService;

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.fromSupplier(
        () -> {
          var request = exchange.getRequest();
          var header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

          if (StringUtils.hasText(header)) {
            var token = header.substring(AUTH_PREFIX.length()).trim();
            List<Role> roles;
            Optional<String> email;

            try {
              roles = this.tokenService.getRoles(token);
              email = this.tokenService.getEmail(token);
            } catch (Throwable ex) {
              throw new BadCredentialsException("Invalid jwt token");
            }

            if (roles.isEmpty() || email.isEmpty()) {
              throw new BadCredentialsException("roles or email is missing in the jwt token");
            }

            return new UsernamePasswordAuthenticationToken(
                email.get(), token, AuthorityUtil.getAuthorityList(roles));
          }

          return new AnonymousAuthenticationToken(
              ANONYMOUS, ANONYMOUS, AuthorityUtil.getAnonymousAuthority());
        });
  }
}
