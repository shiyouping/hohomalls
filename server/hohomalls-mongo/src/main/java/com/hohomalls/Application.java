package com.hohomalls;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

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

  /** Transaction Manager. Needed to allow execution of changeSets in transaction scope. */
  @Bean
  public MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
    return new MongoTransactionManager(mongoTemplate.getMongoDatabaseFactory());
  }
}
