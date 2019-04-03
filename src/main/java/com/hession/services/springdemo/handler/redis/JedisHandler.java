package com.hession.services.springdemo.handler.redis;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCommands;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * * @description: 使用Jedis客户端
 * * @create: 2019-03-30 15:53
 * *
 *
 * @author hession
 * redis操作具体处理类
 **/
@Component
@Slf4j
public class JedisHandler extends RedisBaseHandler{

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁;
     * P.S.
     * 1. 单机版分布式锁 SETNX, 所谓 SETNX, 是「SET if Not eXists」的缩写;
     * 2. SET resource_name my_random_value NX PX 30000 因此当多个客户端去争抢执行上锁或解锁代码时,最终只会有一个客户端执行成功.同时 set 命令还可以指定key的有效期,这样即使当前客户端奔溃,过一段时间锁也会被 redis 自动释放,这就给了其它客户端获取锁的机会.
     * 3. 由于 spring-boot 提供的 redisTemplate.opsForValue().set() 命令是因为 spring-boot 对 jedis 的封装中没有返回 set 命令的返回值,这就导致上层没有办法判断 set 执行的结果, 因此需要通过 execute 方法调用 RedisCallback 去拿到底层的 Jedis 对象,来直接调用 set 命令
     * 4. 可以参考 spring-boot-starter-redis 高级版本中的 .setIfAbsent() 方法
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @param timeout  锁过期时间
     * @param timeUnit 锁过期单位
     * @return Boolean
     */
    public Boolean acquireLock(String lockKey, String clientId, Long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.execute(redisConnection -> {
            JedisCommands jedisConnection = (JedisCommands) redisConnection.getNativeConnection();
            // String nxxx,NX|XX; String expx,EX|PX, EX = seconds, PX = milliseconds;
            String result = jedisConnection.set(lockKey, clientId, "NX", "EX", timeUnit.toSeconds(timeout));
            // 如果setNX成功,返回"OK";如果setNX失败,返回null;
            return !org.springframework.util.StringUtils.isEmpty(result);
        }, true);
    }
}
