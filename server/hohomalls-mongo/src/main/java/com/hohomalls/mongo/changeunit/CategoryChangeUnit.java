package com.hohomalls.mongo.changeunit;

import com.hohomalls.mongo.common.Author;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

/**
 * CategoryChangeUnit.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 2/1/2022
 */
@ChangeUnit(id = "CategoryChangeUnit", order = "1", author = Author.RICKY)
public class CategoryChangeUnit {

  @Execution
  public void execution() {}

  @RollbackExecution
  public void rollbackExecution() {}
}
