package com.smartjob.auth.common.apps;

import com.myzlab.k.DynamicObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisApp {
    
    private static final String ACCESS_TOKEN_TAG = "AT_SMJ";
    
    public static final String APP_USER_ID = "appUserId";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    /* Access token */
    public void putAccessToken(
        final UUID uuid,
        final Integer accessTokenExpirationTime,
        final Long appUserId
    ) {
        put(
            String.format("%s:%s", ACCESS_TOKEN_TAG, uuid.toString()),
            DynamicObject.create()
                .add(APP_USER_ID, appUserId)
            .toMap(), 
            accessTokenExpirationTime
        );
    }
    
    public Boolean existsAccessToken(
        final UUID uuid
    ) {
        return exists(String.format("%s:%s", ACCESS_TOKEN_TAG, uuid.toString()));
    }
    
    public Boolean removeAccessToken(
        final UUID uuid
    ) {
        return remove(String.format("%s:%s", ACCESS_TOKEN_TAG, uuid.toString()));
    }
    
    public Map<String, Object> getAccessTokenData(
        final UUID uuid
    ) {
        return redisTemplate
            .opsForHash()
            .entries(String.format("%s:%s", ACCESS_TOKEN_TAG, uuid.toString()));
    }
    
    /* Privates */
    private void put(
        final String key,
        final HashMap<String, Object> data,
        final Integer expirationTime
    ) {
        redisTemplate.opsForHash().putAll(key, data);
        
        redisTemplate.expire(key, expirationTime, TimeUnit.MINUTES);
    }
    
    private Boolean exists(
        final String key
    ) {
        return redisTemplate.hasKey(key);
    }
    
    private Boolean remove(
        final String key
    ) {
        return redisTemplate.delete(key);
    }
}
