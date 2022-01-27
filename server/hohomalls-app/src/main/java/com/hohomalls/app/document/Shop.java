package com.hohomalls.app.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Shop.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@Document("shops")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Shop extends BaseDoc {

  @Indexed(unique = true)
  private String sellerId;

  @Indexed(unique = true)
  private String name;

  private String description;

  /** Based on all items sold by this shop. */
  private Double rating;

  private ShopStatus status;

  public enum ShopStatus {
    OPEN,
    CLOSED
  }
}
