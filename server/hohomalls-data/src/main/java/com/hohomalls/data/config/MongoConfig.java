package com.hohomalls.data.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * MongoDB configurations.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/4/2021
 */
@Configuration
@RequiredArgsConstructor
@EnableReactiveMongoRepositories(basePackages = "com.hohomalls")
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  private final MongoProperties mongoProperties;

  @Override
  protected boolean autoIndexCreation() {
    return this.mongoProperties.isAutoIndexCreation();
  }

  @Override
  protected void configureClientSettings(MongoClientSettings.Builder builder) {
    builder.credential(
        MongoCredential.createCredential(
            this.mongoProperties.getUsername(),
            this.mongoProperties.getAuthenticationDatabase(),
            this.mongoProperties.getPassword()));
  }

  @Override
  protected String getDatabaseName() {
    return this.mongoProperties.getDatabase();
  }
}
