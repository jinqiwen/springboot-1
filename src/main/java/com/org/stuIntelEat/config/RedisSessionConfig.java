package com.org.stuIntelEat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.executable.ValidateOnExecution;
/*import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;*/

/**
 * session会话
 * Created by 瓦力.
 */
@Configuration
@PropertySource(value = "classpath:application.properties")
/*@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)*/
public class RedisSessionConfig {


    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

        return new StringRedisTemplate(factory);
    }
}
