package com.hohomalls.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;

/**
 * The parent class of all mongo documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
public abstract class Base {
  @Id private String id;
  private OffsetDateTime createdDateTime;
  private OffsetDateTime updatedDateTime;
}
