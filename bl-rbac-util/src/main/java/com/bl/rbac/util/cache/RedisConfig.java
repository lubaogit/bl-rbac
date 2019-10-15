package com.bl.rbac.util.cache;

import com.bl.rbac.util.cache.redis.Redis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author jh
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory factory;

    @Bean
    public RedisTemplate<String,Object> getRedisTemplate(){
        RedisTemplate <String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public HashOperations<String,String,Object> getHashOperation(){
        return getRedisTemplate().opsForHash();
    }

    @Bean
    public SetOperations<String,Object> getSetOperations(){
        return getRedisTemplate().opsForSet();
    }

    @Bean
    public UserInfoCache getUserInfoCache(){return new UserInfoCache();}

    @Bean
    public Redis getRedis(){return new Redis();}
}
