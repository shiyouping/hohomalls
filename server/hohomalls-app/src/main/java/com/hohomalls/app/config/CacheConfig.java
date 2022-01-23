package com.hohomalls.app.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * CacheConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 22/1/2022
 */
@EnableCaching
@Configuration
public class CacheConfig {
  // There is no fluent integration between @Cacheable and reactive frameworks.
  // See the example in the class of DirectoryServiceImpl
}
