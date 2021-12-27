package com.hohomalls.app.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Item.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends BaseDoc {

  private String categoryId;
  private String shopId;
  private String title;
  private String brand;
  private Integer quantity;
  private String description;
  private Condition condition;
  private List<Price> prices;
  private List<String> highlights;
  private Float rating;

  public enum Condition {
    NEW,
    RENEWED,
    USED
  }

  @Data
  public static class Price {
    private String name;
    private Double amount;
  }
}
