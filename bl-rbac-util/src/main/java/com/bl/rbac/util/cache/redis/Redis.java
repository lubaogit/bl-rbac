package com.bl.rbac.util.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

public class Redis {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redis存值
     */

    public Boolean set(final String key,final String value){
        Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer <String> serializer = redisTemplate.getStringSerializer();
                redisConnection.set(serializer.serialize(key),serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    /**
     * redis取值
     */

    public String get(String key){
        String result =(String)redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = redisConnection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    /**
     * 设置redis有效期
     */
    public Boolean setExpire(String key,long expire){
        redisTemplate.expire(key,expire, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 删除一个redis缓存
     */
    public Boolean deleteRedis(final String key){
        redisTemplate.delete(key);
        return true;
    }


}
