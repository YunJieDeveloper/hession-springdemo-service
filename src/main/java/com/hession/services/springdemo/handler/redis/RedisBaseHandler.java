package com.hession.services.springdemo.handler.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: hession-springdemo-service
 * @description:
 * @author: Hession
 * @create: 2019-03-30 20:37
 * <p>
 * redis 公共基本操作
 **/
@Slf4j
public class RedisBaseHandler {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    /***======================================= String类型 opsForValue======================================= **/
    /**
     * @param key 键值
     * @return 返回key计数值  key永久有效
     * @Author hession
     * @Description 按1自增
     * @Date 18:11 2019-03-30
     */
    public Long incre(String key) {
        return incre(key, 1, null);
    }

    /**
     * @param key        键值
     * @param expireTime 有效时间 秒
     * @return 返回key计数值
     * @Author hession
     * @Description 按1自增
     * @Date 18:11 2019-03-30
     */
    public Long incre(String key, long expireTime) {
        return incre(key, 1, expireTime);
    }

    /**
     * @param key        键值
     * @param interval   自增数，按几自增
     * @param expireTime 有效时间 秒
     * @return
     * @Author hession
     * @Description 自增
     * @Date 18:11 2019-03-30
     */
    public Long incre(String key, int interval, Long expireTime) {

        if (Objects.isNull(expireTime) || expireTime == 0) {
            return stringRedisTemplate.opsForValue().increment(key, interval);
        } else {
            if (expireTime < 0) {
                throw new IllegalArgumentException("expireTime must be large than Zero");
            }
            if (StringUtils.isBlank(this.get(key))//等同this.ttl(key) == -2
                    || Objects.isNull(this.ttl(key))
                    || this.ttl(key) == -1) {
                stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
                return stringRedisTemplate.opsForValue().increment(key, interval);
            } else {
                return stringRedisTemplate.opsForValue().increment(key, interval);
            }
        }
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    /**
     * @param key   永久有效
     * @param value
     * @return
     * @author hession
     * @description redis 放string类型值
     */
    public void set(String key, String value) {
        this.set(key, value, null);
    }

    /**
     * @param key
     * @param value
     * @param expireTime 有效时间 单位：秒
     * @return
     * @throws IllegalArgumentException
     * @Author hession
     * @Description //TODO
     * @Date 21:23 2019-03-30
     */
    public void set(String key, String value, Long expireTime) throws IllegalArgumentException {
        if (Objects.nonNull(expireTime) && expireTime != 0) {
            if (expireTime < 0) {
                throw new IllegalArgumentException("expireTime must be large than Zero");
            }
            stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        } else {
            stringRedisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * @param key
     * @param value
     * @param offset 偏移位
     * @return
     * @Description 该方法是用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
     */
    public void set(String key, String value, int offset) {
        stringRedisTemplate.opsForValue().set(key, value, offset);
    }


    /**
     * @param key   永久有效
     * @param value
     * @return
     * @author hession
     * @description key不存在时set并返回true;若存在则set失败,返回false。
     */
    public Boolean setIfAbsent(String key, String value) {
        return this.setIfAbsent(key, value, null);
    }

    /**
     * @param key
     * @param value
     * @param expireTime 有效时间 单位：秒
     * @return
     * @author hession
     * @description key不存在时set并返回true;若存在则set失败,返回false。
     */
    public Boolean setIfAbsent(String key, String value, Long expireTime) {
        return this.setIfAbsent(key, value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * @param key
     * @param value
     * @param expireTime
     * @param unit
     * @return
     * @author hession
     * @description key不存在时set并返回true;若存在则set失败,返回false。
     */
    public Boolean setIfAbsent(String key, String value, Long expireTime, @NotNull TimeUnit unit) {
        if (Objects.nonNull(expireTime) && expireTime != 0) {
            if (expireTime < 0) {
                throw new IllegalArgumentException("expireTime must be large than Zero");
            }
            return stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, unit);

        }
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value);
    }


    /***======================================= list   ======================================= **/


    /**
     * @param key
     * @param start 下标索引起始点  0代表第一个元素
     * @param end   下标索引结束点  -1代表最后一个元素
     * @return
     * @author hession
     * @description //获取存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
     */
    public List<String> range(String key, long start, long end) {
        return stringRedisTemplate.opsForList().range(key, start, end);
    }


    /**
     * @param key
     * @param value
     * @return
     * @author hession
     * @description 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     */
    public Long rightPush(String key, String value) {
        return stringRedisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @param key
     * @param values 数组方式
     * @return
     * @author hession
     * @description 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     */
    public Long rightPushAll(String key, String... values) {
        return stringRedisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * @param key
     * @param values list集合方式
     * @return
     * @author hession
     * @description 将所有指定的值插入存储在键的列表的头部。如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     */
    public Long rightPushAll(String key, List<String> values) {
        return stringRedisTemplate.opsForList().rightPushAll(key, values);
    }


    /**
     * @param
     * @param key
     * @return 最右边的元素
     * @author hession
     * @description 弹出最右边的元素，弹出之后该值在列表中将不复存在
     */
    public String rightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    
    /**
     * @author hession
     * @description //TODO 
     * @param
     * @return 
     */
    public String rightPop(String key,long timeout, TimeUnit unit) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }


    /***======================================= ttl   ======================================= **/
    /**
     * @param key
     * @return 剩余有效时间
     * @Author hession
     * @Description # 返回-1：key存在,但没有设置剩余生存时间(永久存在)
     * 返回-2：key不存在
     * <p>
     * 注意：在 Redis 2.8 以前，当 key 不存在，或者 key 没有设置剩余生存时间时，命令都返回 -1 。
     * @Date 20:53 2019-03-30
     */
    public Long ttl(String key) {
        return stringRedisTemplate.getExpire(key);
    }


    /**
     * @param key
     * @param expireTime 生效时间 单位：秒
     * @return
     * @author hession
     * @description //为key设置生效时间
     * <p>
     * </>key不存在时，返回false；key存在返回true。
     */
    public Boolean expire(String key, long expireTime) {
        return this.expire(key, expireTime, TimeUnit.SECONDS);
    }

    public Boolean expire(String key, long expireTime, @NotNull TimeUnit unit) {
        if (expireTime <= 0) {
            throw new IllegalArgumentException("expireTime must be large than Zero");
        }

        return stringRedisTemplate.expire(key, expireTime, unit);
    }

    /***======================================= del   ======================================= **/
    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    /***======================================= eval   ======================================= **/
    /**
     * 释放Redis全局锁
     * P.S.
     * 1. 锁的释放必须使用lua脚本,保证操作的原子性;使用 lua 脚本使 get 与 del 方法执行成为原子性
     *
     * @param lockKey  加锁键
     * @param clientId 加锁客户端唯一标识(采用UUID)
     * @return Boolean
     */
    public Boolean releaseLock(String lockKey, String clientId) {
        // 释放锁的时候,有可能因为持锁之后方法执行时间大于锁的有效期,此时有可能已经被另外一个线程持有锁,所以不能直接删除
        return (Boolean) stringRedisTemplate.execute((RedisCallback<Object>) connection -> {
            // 为了保证锁被锁的持有者释放,使用lua脚本删除redis中匹配value的key,可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            return connection.<Boolean>eval(script.getBytes(), ReturnType.BOOLEAN, 1, lockKey.getBytes(), clientId.getBytes());
        });
    }
}
