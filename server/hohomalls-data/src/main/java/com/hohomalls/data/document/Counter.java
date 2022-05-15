package com.hohomalls.data.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;

/**
 * Counter.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 10/5/2022
 */
@Data
@Builder
@Document("counters")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Counter extends BaseDoc {

  @NotNull
  @Indexed(unique = true)
  private Type type;

  @Nonnegative private long value;

  public enum Type {
    ORDER,
    PAYMENT
  }
}
