package com.rms.rms.common.config;

import com.rms.rms.common.config.properties.CacheServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * homertruong
 */

@Configuration
@EnableCaching
public class CacheServerConfig {

    @Autowired
    private CacheServerProperties cacheServerProperties;

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(cacheServerProperties.getRedis().getDefaultExpired());
        return cacheManager;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(cacheServerProperties.getRedis().getHost());
        factory.setPort(cacheServerProperties.getRedis().getPort());
        factory.setPassword(cacheServerProperties.getRedis().getPassword());
        factory.setTimeout(cacheServerProperties.getRedis().getTimeout());
        factory.setPoolConfig(poolConfiguration());
        factory.setUsePool(true);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public JedisPoolConfig poolConfiguration() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMinIdle(cacheServerProperties.getRedis().getMinIdle());
        config.setMaxTotal(cacheServerProperties.getRedis().getMaxTotal());
        config.setBlockWhenExhausted(cacheServerProperties.getRedis().isBlockWhenExhausted());
        return config;
    }

    @Bean
    RedisTemplate<String, String> redisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer()); // to make the keys in redis clearly
        redisTemplate.setValueSerializer(new StringRedisSerializer()); // to make the values in redis clearly
        return redisTemplate;
    }
}
