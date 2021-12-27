package com.hohomalls.app.document;

import com.hohomalls.data.pojo.BaseDoc;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class Order extends BaseDoc {}
