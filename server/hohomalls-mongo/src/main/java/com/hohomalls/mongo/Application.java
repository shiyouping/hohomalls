package com.hohomalls.mongo;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Application.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 29/12/2021
 */
@EnableMongock
@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.hohomalls.mongo.repository"})
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder().sources(Application.class).run(args);
  }

  /** Transaction Manager. Needed to allow execution of changeSets in transaction scope. */
  @Bean
  public MongoTransactionManager transactionManager(MongoTemplate mongoTemplate) {
    TransactionOptions transactionalOptions =
        TransactionOptions.builder()
            .readConcern(ReadConcern.MAJORITY)
            .readPreference(ReadPreference.primary())
            .writeConcern(WriteConcern.MAJORITY.withJournal(true))
            .build();
    return new MongoTransactionManager(
        mongoTemplate.getMongoDatabaseFactory(), transactionalOptions);
  }
}
