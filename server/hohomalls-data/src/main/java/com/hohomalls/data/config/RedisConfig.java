package com.hohomalls.data.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.hohomalls.core.common.Global.BASE_PACKAGE;

/**
 * The class of RedisConfig.
 *
 * <p>1. For primitive and simple types, e.g. String, Integer, Operation APIs of RedisTemplate are
 * preferred.
 *
 * <p>2. For other advanced operations, Command APIs of ReactiveRedisConnection are preferred
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
  @NotNull
  public ReactiveRedisConnection reactiveRedisConnection(
      @NotNull ReactiveRedisConnectionFactory redisConnectionFactory) {
    checkNotNull(redisConnectionFactory, "redisConnectionFactory cannot be null");
    return redisConnectionFactory.getReactiveConnection();
  }

  @Bean
  @NotNull
  public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
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
  @NotNull
  public ReactiveRedisTemplate<?, ?> reactiveRedisTemplate(
      @NotNull ReactiveRedisConnectionFactory redisConnectionFactory,
      @NotNull ObjectMapper mapper) {
    checkNotNull(redisConnectionFactory, "redisConnectionFactory cannot be null");
    checkNotNull(mapper, "mapper cannot be null");

    return new ReactiveRedisTemplate<>(
        redisConnectionFactory,
        RedisSerializationContext.fromSerializer(new GenericJackson2JsonRedisSerializer(mapper)));
  }
}
