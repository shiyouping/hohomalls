package com.hohomalls.web.security;

import com.hohomalls.core.enumeration.Role;
import com.hohomalls.web.service.TokenService;
import com.hohomalls.web.util.AuthorityUtil;
import com.hohomalls.web.util.HttpHeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static com.hohomalls.core.common.Constant.AUTH_ANONYMOUS;

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

  private final TokenService tokenService;

  @Override
  @SuppressWarnings("PMD")
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.fromSupplier(
        () -> {
          var request = exchange.getRequest();
          var header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
          var token = HttpHeaderUtil.getAuth(header);

          if (token.isPresent()) {
            List<Role> roles;
            Optional<String> email;

            try {
              roles = this.tokenService.getRoles(token.get());
              email = this.tokenService.getEmailFromJwt(token.get());
            } catch (Exception ex) {
              throw new BadCredentialsException("Invalid jwt token", ex);
            }

            if (roles.isEmpty() || email.isEmpty()) {
              throw new BadCredentialsException("roles or email is missing in the jwt token");
            }

            return new UsernamePasswordAuthenticationToken(
                email.get(), token.get(), AuthorityUtil.getAuthorityList(roles));
          }

          return new AnonymousAuthenticationToken(
              AUTH_ANONYMOUS, AUTH_ANONYMOUS, AuthorityUtil.getAnonymousAuthority());
        });
  }
}
