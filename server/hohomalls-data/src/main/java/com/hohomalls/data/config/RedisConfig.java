package com.hohomalls.data.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import static com.hohomalls.core.constant.Global.BASE_PACKAGE;

/**
 * The class of RedisConfig.
 *
 * @author ricky.shiyouping@gmail.com
 * @since 15/7/2021
 */
@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories(basePackages = BASE_PACKAGE)
public class RedisConfig {

  private final RedisProperties redisProperties;

  @Bean
  public ReactiveRedisConnectionFactory lettuceConnectionFactory() {
    var clientConfig =
        this.redisProperties.isSsl()
            ? LettuceClientConfiguration.builder().useSsl().build()
            : LettuceClientConfiguration.builder().build();

    var redisConfig =
        new RedisStandaloneConfiguration(
            this.redisProperties.getHost(), this.redisProperties.getPort());
    redisConfig.setUsername(this.redisProperties.getUsername());
    redisConfig.setPassword(this.redisProperties.getPassword());

    return new LettuceConnectionFactory(redisConfig, clientConfig);
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
