package com.hohomalls.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The class of BasePojo.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 16/7/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasePojo {

  private String id;
  private Instant createdAt;
  private Instant updatedAt;
}
