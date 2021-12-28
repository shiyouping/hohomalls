package com.hohomalls.app.document;

import com.hohomalls.core.enumeration.Currency;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Payment.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/12/2021
 */
@Data
@Builder
@Document("payments")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Payment extends BaseDoc {

  private String orderId;
  private Double amount;
  private Currency currency;
  private PaymentStatus status;

  public enum PaymentStatus {
    SUCCESS,
    ERROR,
    CREATED
  }
}
