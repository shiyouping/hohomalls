package com.hohomalls.data.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static com.hohomalls.core.constant.Global.BASE_PACKAGE;

/**
 * MongoDB configurations.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/4/2021
 */
@Configuration
@RequiredArgsConstructor
@EnableReactiveMongoRepositories(basePackages = BASE_PACKAGE)
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
  protected @NotNull String getDatabaseName() {
    return this.mongoProperties.getDatabase();
  }
}
