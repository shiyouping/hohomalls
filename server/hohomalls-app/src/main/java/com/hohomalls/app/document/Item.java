package com.hohomalls.app.document;

import com.hohomalls.core.pojo.Shipping;
import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.Nonnegative;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

  @NotNull
  @TextIndexed(weight = 10)
  private String title;

  @NotNull
  @TextIndexed(weight = 3)
  private String description;

  private String brand;

  @Nonnegative private Double rating;

  @NotNull private String shopId;

  @NotNull private String categoryId;

  @Nonnegative private Integer quantity;

  @NotNull private Condition condition;

  @NotNull private ItemStatus status;

  @Size(min = 1)
  private List<Price> prices;

  private List<String> highlights;

  @Size(min = 1)
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
