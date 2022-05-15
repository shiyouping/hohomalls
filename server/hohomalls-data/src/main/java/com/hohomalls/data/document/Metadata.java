package com.hohomalls.data.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Metadata.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Data
@Builder
@Document("metadata")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Metadata extends BaseDoc {

  @NotNull @Indexed private String name;

  @NotNull @Indexed private String path;
}
