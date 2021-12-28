package com.hohomalls.app.document;

import com.hohomalls.core.pojo.Address;
import com.hohomalls.core.pojo.Shipping;
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
  private String shopId;
  private String itemId;
  private String buyerId;
  private String sellerId;
  private Integer quantity;
  private LocalDate placedOn;
  private OrderStatus status;
  private Shipping shipping;
  private Address shippingAddress;

  public enum OrderStatus {
    PLACED,
    PAID,
    SHIPPED,
    DELIVERED
  }
}
