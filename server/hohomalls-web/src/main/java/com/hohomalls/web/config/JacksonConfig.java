package com.hohomalls.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * The class of JacksonConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 30/7/2021
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JacksonConfig {

  private final ObjectMapper mapper;

  @PostConstruct
  public void registerModule() {
    // Add UsernamePasswordAuthenticationTokenDeserializer
    mapper.registerModule(new CoreJackson2Module());
    log.info("CoreJackson2Module was registered");
  }
}
