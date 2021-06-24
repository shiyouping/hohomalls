package com.hohomalls.web.filter;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * The class of CorsFilter.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 20/6/2021
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements WebFilter {

  @Override
  public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    if (CorsUtils.isCorsRequest(request)) {
      ServerHttpResponse response = exchange.getResponse();
      HttpHeaders headers = response.getHeaders();
      // TODO add missing controls here
      headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
      headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
      headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type");

      if (request.getMethod() == HttpMethod.OPTIONS) {
        response.setStatusCode(HttpStatus.OK);
        return Mono.empty();
      }
    }

    return chain.filter(exchange);
  }
}
