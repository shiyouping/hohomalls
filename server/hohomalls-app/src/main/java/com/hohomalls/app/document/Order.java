package com.hohomalls.app.document;

import com.hohomalls.core.pojo.Address;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * Order.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@Document("orders")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseDoc {

  private String number;
  private LocalDate placedOn;
  private String itemId;
  private Integer quantity;
  private Address shippingAddress;
  private OrderStatus status;

  public enum OrderStatus {
    PLACED,
    PAID,
    SHIPPED,
    DELIVERED
  }
}
