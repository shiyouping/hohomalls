package com.hohomalls.mongo;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Application.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/12/2021
 */
@EnableMongock
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(Application.class).run(args);
  }
}
