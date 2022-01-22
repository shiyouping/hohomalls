package com.hohomalls.aws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.hohomalls.core.common.Constant.CONFIG_PROPERTY_PREFIX;

/**
 * AwsProperties.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 14/1/2022
 */
@Validated
@ConfigurationProperties(prefix = CONFIG_PROPERTY_PREFIX + "aws")
public record AwsProperties(@NotNull S3 s3) {

  @Validated
  public record S3(@NotEmpty String bucketName) {
  }
}
