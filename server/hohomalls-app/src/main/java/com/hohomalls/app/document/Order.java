package com.hohomalls.app.document;

import com.hohomalls.core.pojo.Address;
import com.hohomalls.core.pojo.Shipping;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

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

  @NotNull
  @Indexed(unique = true)
  private String number;

  @NotNull
  @TextIndexed(weight = 3)
  private String description;

  @NotNull private String shopId;

  @NotNull private String itemId;

  @NotNull private String buyerId;

  @NotNull private String sellerId;

  @Positive @NotNull private Integer quantity;

  @NotNull private Instant placeAt;

  @NotNull private OrderStatus status;

  @NotNull private Shipping shipping;

  @NotNull private Address shippingAddress;

  public enum OrderStatus {
    PLACED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    REFUNDING,
    REFUNDED,
  }
}
