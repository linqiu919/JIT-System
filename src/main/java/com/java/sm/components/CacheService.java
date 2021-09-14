package com.java.sm.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName CacheService.java
 * @DescriPtion 缓存
 * @CreateTime 2021年07月03日 11:52:00
 */
@Component
public class CacheService {

    public static final long defaultTime = 300;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void setCache(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public void setCache(String key,String value,long second){
        stringRedisTemplate.opsForValue().set(key,value,second, TimeUnit.SECONDS);
    }

    public void setCacheWithDefaultTime(String key,String  value){
        stringRedisTemplate.opsForValue().set(key,value,defaultTime, TimeUnit.SECONDS);
    }

    public String  getCache(String key){
        if(hasKey(key)){
            return stringRedisTemplate.opsForValue().get(key);
        }
        return "";
    }

    public boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    public void  removeCache(String key){
        stringRedisTemplate.delete(key);
    }
}
