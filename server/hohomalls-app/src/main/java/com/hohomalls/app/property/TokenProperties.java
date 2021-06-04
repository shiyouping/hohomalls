package com.hohomalls.app.property;

import com.hohomalls.core.constant.Properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * The class of TokenProperties.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 3/6/2021
 */
@Data
@Validated
@ConfigurationProperties(prefix = Properties.PREFIX + "token")
public class TokenProperties {

  @NotBlank private String privateKey = "private.key";
  @NotBlank private String publicKey = "public.key";
  @Positive private Long lifespan = 24L * 7;
}
