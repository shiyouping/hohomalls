package com.hohomalls.data.pojo;

import com.hohomalls.core.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * The parent class of all data documents.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDoc extends BasePojo {
  private String id;
  private Instant createdAt;
  private Instant updatedAt;
}
