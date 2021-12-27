package com.hohomalls.app.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * See Model Tree Structures with an Array of Ancestors at
 * https://docs.mongodb.com/v4.4/applications/data-models-tree-structures/
 *
 * @author ricky.shiyouping@gmail.com
 * @since 27/12/2021
 */
@Data
@Builder
@Document("categories")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseDoc {

  private String description;
  private String parentId;
  private List<String> ancestorIds;
}
