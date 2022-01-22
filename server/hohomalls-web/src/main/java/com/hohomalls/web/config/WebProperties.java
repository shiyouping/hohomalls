package com.hohomalls.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.hohomalls.core.common.Constant.CONFIG_PROPERTY_PREFIX;

/**
 * WebProperties.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 9/1/2022
 */
@Validated
@ConfigurationProperties(prefix = CONFIG_PROPERTY_PREFIX + "web")
public record WebProperties(@NotNull Multipart multipart,
                            @NotNull Token token) {

  @Validated
  public record Multipart(@NotNull FileSize maxFileSize,
                          @NotBlank String baseStorageUrl,
                          @NotEmpty List<String> contentTypes) {
  }

  @Validated
  public record FileSize(@Positive long image,
                         @Positive long video) {
  }

  @Validated
  public record Token(@NotBlank String privateKey,
                      @NotBlank String publicKey,
                      @Positive long lifespan) {
  }
}
