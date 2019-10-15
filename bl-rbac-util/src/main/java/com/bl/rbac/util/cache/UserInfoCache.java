package com.bl.rbac.util.cache;

import com.alibaba.fastjson.JSON;
import com.bl.rbac.common.entity.UserEntity;
import com.bl.rbac.common.util.MapObjUtil;
import com.bl.rbac.util.cache.redis.Redis;
import com.bl.rbac.util.exception.RedisCacheException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.util.StringUtils;

import java.util.Map;

public class UserInfoCache {

    @Autowired
    private Redis redis;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    private static final Logger log = LoggerFactory.getLogger(UserInfoCache.class);

    private String getKey(String userId) {
        return RedisConstant.format(RedisConstant.USER_INFO, userId);

    }

    ;

    /**
     * 用户信息缓存
     *
     * @param userEntity
     * @return
     */
    public Boolean setUserInfo(UserEntity userEntity) {
        if(userEntity ==null){
            throw new RedisCacheException("用户信息为空...");
        }
        if(StringUtils.isEmpty(userEntity.getId())){
            throw new RedisCacheException("用户Id为空...");
        }
        this.excute(getKey(String.valueOf(userEntity.getId())), userEntity);
        return true;
    }


    /**
     * 删除用户信息缓存
     */
    public Boolean deleteUserInfoCache(String userId) {
        if(StringUtils.isEmpty(userId)){
            throw new RedisCacheException("用户ID为空...");
        }
        redis.deleteRedis(getKey(userId));
        return true;
    }

    /**
     * 修改用户缓存信息
     */
    public Boolean updateUserInfoCache(UserEntity userEntity) {
        if(userEntity ==null){
            throw new RedisCacheException("用户信息为空...");
        }
        if(StringUtils.isEmpty(userEntity.getId())){
            throw new RedisCacheException("用户id为空...");
        }
        log.info("修改用户信息缓存userId：{}>>>用户信息：{}", userEntity.getId(), JSON.toJSONString(userEntity));
        redis.deleteRedis(getKey(String.valueOf(userEntity.getId())));
        this.excute(getKey(String.valueOf(userEntity.getId())), userEntity);
        return true;
    }

    public UserEntity getUserInfoCache(String userId) {

        if(StringUtils.isEmpty(userId)){
            throw new RedisCacheException("用户ID为空...");
        }
        log.info("获取缓存信息userId:{}", userId);
        String key = getKey(userId);
        Map<String, Object> map = hashOperations.entries(key);
        if (map == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructType(UserEntity.class);

        UserEntity userEntity = mapper.convertValue(map, javaType);
        return userEntity;
    }

    private void excute(String key, UserEntity userEntity) {
        log.info("新增缓存key:{}>>>>>值为：{}", key, JSON.toJSONString(userEntity));
        hashOperations.putAll(key, MapObjUtil.object2Map(userEntity));
        redis.setExpire(key, RedisConstant.REDIS_TIME_OUT);
    }


}

