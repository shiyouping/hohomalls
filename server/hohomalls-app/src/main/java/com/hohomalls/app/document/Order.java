package com.hohomalls.app.document;

import com.hohomalls.mongo.document.Base;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The order document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@Builder
@Document("orders")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Base {
  private String dummyField;
}
