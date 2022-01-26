package com.hohomalls.app.document;

import com.hohomalls.core.pojo.Shipping;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Item.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@Document("items")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseDoc {

  @TextIndexed(weight = 10)
  private String title;

  @TextIndexed(weight = 3)
  private String description;

  private String brand;
  private Float rating;
  private String shopId;
  private String categoryId;
  private Integer quantity;
  private Condition condition;
  private ItemStatus status;
  private List<Price> prices;
  private List<String> highlights;
  private List<Shipping> shippings;
  private Map<String, String> attributes;

  public enum Condition {
    NEW,
    RENEWED,
    USED
  }

  public enum ItemStatus {
    UNAVAILABLE,
    AVAILABLE,
    SOLD,
  }

  @Data
  public static class Price {
    private String name;
    private Double amount;
  }
}
