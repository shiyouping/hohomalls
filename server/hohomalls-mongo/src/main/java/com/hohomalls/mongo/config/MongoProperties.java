package com.hohomalls.mongo.config;

import com.hohomalls.core.constant.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * The Java class associated with the properties file.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 24/4/2021
 */
@Data
@Validated
@ConfigurationProperties(prefix = Properties.PREFIX + "mongo")
public class MongoProperties {

  @NotNull private String databaseName;
}
