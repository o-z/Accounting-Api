package com.javacase.accountingapi.config.redis;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class RedisConfiguration {

  @Value("${spring.data.redis.host}")
  private String host;

  @Value("${spring.data.redis.port}")
  private int port;

  @Value("${spring.data.redis.connect-timeout}")
  private int timeout;

  @Value("${spring.data.redis.password}")
  private String password;

  public static final String MANAGER_LOCK = "manager-lock";

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setPort(port);
    redisStandaloneConfiguration.setDatabase(0);
    redisStandaloneConfiguration.setPassword(password);
    JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
        .connectTimeout(Duration.ofSeconds(timeout))
        .build();

    return new JedisConnectionFactory(redisStandaloneConfiguration,
        jedisClientConfiguration);
  }

  @Bean
  RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    return redisTemplate;
  }


  @Bean("managerLockRegistry")
  public RedisLockRegistry managerLockRegistry() {
    return new RedisLockRegistry(jedisConnectionFactory(), MANAGER_LOCK,
        Duration.ofSeconds(5).toMillis());
  }
}
