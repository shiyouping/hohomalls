package com.hohomalls.data.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * ValidationConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 28/1/2022
 */
@Configuration
public class ValidationConfig {

  @Bean
  @ConditionalOnMissingBean(LocalValidatorFactoryBean.class)
  public LocalValidatorFactoryBean localValidatorFactoryBean() {
    return new LocalValidatorFactoryBean();
  }

  @Bean
  @ConditionalOnMissingBean(ValidatingMongoEventListener.class)
  public ValidatingMongoEventListener validatingMongoEventListener(
      LocalValidatorFactoryBean localValidatorFactoryBean) {
    return new ValidatingMongoEventListener(localValidatorFactoryBean);
  }
}
