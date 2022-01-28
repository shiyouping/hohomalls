package com.hohomalls.data.pojo;

import com.hohomalls.core.pojo.BasePojo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
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

  @NotNull private Instant createdAt;

  @NotNull private Instant updatedAt;
}
