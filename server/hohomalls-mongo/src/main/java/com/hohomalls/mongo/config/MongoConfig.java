package com.hohomalls.mongo.config;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * MongoDB configurations.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/4/2021
 */
@AllArgsConstructor
@EnableReactiveMongoRepositories
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  private final MongoProperties mongoProperties;

  @Override
  protected String getDatabaseName() {
    return this.mongoProperties.getDatabaseName();
  }
}
