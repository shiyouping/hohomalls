package com.hohomalls.web.handler;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

/**
 * The class of GlobalErrorAttributes.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/6/2021
 */
@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(
      ServerRequest request, ErrorAttributeOptions options) {
    // TODO Customize the error response
    return super.getErrorAttributes(request, options);
  }
}
