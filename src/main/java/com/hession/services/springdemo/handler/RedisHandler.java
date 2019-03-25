package com.hession.services.springdemo.handler;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author hession
 * redis操作具体处理类
 **/
@Service
public class RedisHandler {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 记录有效时间key数*/
    public Long incre(String key, int expireTime) {
        if (StringUtils.isBlank(stringRedisTemplate.opsForValue().get(key))
           || stringRedisTemplate.getExpire(key)==null
           || stringRedisTemplate.getExpire(key)==-1 ){
           return stringRedisTemplate.opsForValue().increment(key, expireTime);
        }else {
           return stringRedisTemplate.opsForValue().increment(key);
        }
    }

}
