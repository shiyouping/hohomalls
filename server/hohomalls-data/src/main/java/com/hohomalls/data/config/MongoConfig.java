package com.hohomalls.data.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static com.hohomalls.core.common.Constant.CONFIG_BASE_PACKAGE;

/**
 * MongoDB configurations.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 23/4/2021
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableReactiveMongoRepositories(basePackages = CONFIG_BASE_PACKAGE)
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  private final MongoProperties mongoProperties;

  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate(ReactiveMongoDatabaseFactory factory) {
    return new ReactiveMongoTemplate(factory);
  }

  @Bean
  public ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory factory) {
    return new ReactiveMongoTransactionManager(factory);
  }

  @Override
  protected boolean autoIndexCreation() {
    return this.mongoProperties.isAutoIndexCreation();
  }

  @Override
  protected void configureClientSettings(MongoClientSettings.Builder builder) {
    var connectionString =
        "mongodb://%s:%s".formatted(this.mongoProperties.getHost(), this.mongoProperties.getPort());
    builder.applyConnectionString(new ConnectionString(connectionString));
    builder.credential(
        MongoCredential.createCredential(
            this.mongoProperties.getUsername(),
            this.mongoProperties.getAuthenticationDatabase(),
            this.mongoProperties.getPassword()));

    MongoConfig.log.info("MongoDb Connection String = {}", connectionString); // NOPMD
  }

  @Override
  protected @NotNull String getDatabaseName() {
    return this.mongoProperties.getDatabase();
  }
}
