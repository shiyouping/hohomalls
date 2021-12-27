package com.hohomalls.app.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Review.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@Document("reviews")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Review extends BaseDoc {

  private String buyerId;
  private String itemId;
  private String shopId;
  private Integer rating;
  private String description;
}
