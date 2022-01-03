package com.hohomalls.mongo.changeunit;

import com.hohomalls.mongo.util.FileReader;
import io.mongock.api.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * ChangeUnit0001.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 2/1/2022
 */
@RequiredArgsConstructor
@ChangeUnit(id = "ChangeUnit0001", order = "1", systemVersion = "1.0.0")
public class ChangeUnit0001 {

  private static final String CATEGORIES = "categories";
  private final MongoTemplate mongoTemplate;

  @BeforeExecution
  public void beforeExecution() {
    if (!this.mongoTemplate.collectionExists(ChangeUnit0001.CATEGORIES)) {
      this.mongoTemplate.createCollection(ChangeUnit0001.CATEGORIES);
    }
  }

  @Execution
  public void execution() {
    this.mongoTemplate.executeCommand(FileReader.read("change-unit/0001.json"));
  }

  @RollbackBeforeExecution
  public void rollbackBeforeExecution() {
    this.mongoTemplate.dropCollection(ChangeUnit0001.CATEGORIES);
  }

  @RollbackExecution
  public void rollbackExecution() {
    this.mongoTemplate.executeCommand(FileReader.read("change-unit/0001-rollback.json"));
  }
}
