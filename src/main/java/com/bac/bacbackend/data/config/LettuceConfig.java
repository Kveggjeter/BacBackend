package com.bac.bacbackend.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Configuration for Lettuce, for handling Redis requests through the Spring framework.
 */
@Configuration
    @ComponentScan("com.bac.bacbackend")
    @EnableRedisRepositories (basePackages = "com.bac.bacbackend.data.repository")
    public class LettuceConfig {

    /**
     * Currently redundant, as our implementations are of the CRUDRepository
     * @return A template for interacting with Redis
     */
    @Bean
        public RedisTemplate<String, Object> redisTemplate() {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(lettuceConnectionFactory());
            return template;
        }

    /**
     * @return the factory method for connecting to Redis
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() { return new LettuceConnectionFactory(); }

    }

