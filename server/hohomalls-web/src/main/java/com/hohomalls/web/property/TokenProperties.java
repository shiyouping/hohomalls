package com.hohomalls.web.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import static com.hohomalls.core.common.Global.PROPERTY_PREFIX;

/**
 * The class of TokenProperties.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = PROPERTY_PREFIX + "token")
public class TokenProperties {

  @NotBlank private String privateKey;
  @NotBlank private String publicKey;
  @Positive private Long lifespan = 24L * 7;
}
