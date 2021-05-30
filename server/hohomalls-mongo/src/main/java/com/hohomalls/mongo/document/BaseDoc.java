package com.hohomalls.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

/**
 * The parent class of all mongo documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDoc {
  @Id private String id;
  private Instant createdAt;
  private Instant updatedAt;
}
