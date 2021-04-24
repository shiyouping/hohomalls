package com.hohomalls.app.document;

import com.hohomalls.mongo.document.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The item document.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@Document("items")
@EqualsAndHashCode(callSuper = true)
public class Item extends Base {}
