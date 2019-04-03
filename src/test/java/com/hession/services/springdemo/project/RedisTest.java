package com.hession.services.springdemo.project;

import com.hession.services.springdemo.BaseSpringbootTests;
import com.hession.services.springdemo.handler.redis.JedisHandler;
import com.hession.services.springdemo.handler.redis.LettuceRedisHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: hession-springdemo-service
 * @description:
 * @author: Hession
 * @create: 2019-03-30 18:18
 **/
public class RedisTest extends BaseSpringbootTests {

    @Autowired
    JedisHandler jedisHandler;

    @Autowired
    LettuceRedisHandler lettuceRedisHandler;

    @Test
    public void jedisLockTest() throws Exception {

        Boolean aBoolean = jedisHandler.acquireLock("lockKey", "lockValue", 3l, TimeUnit.SECONDS
        );

        System.out.println(aBoolean);
        TimeUnit.SECONDS.sleep(4);

        Boolean aBoolean1 = jedisHandler.releaseLock("lockKey", "lockValue");
        System.out.println(aBoolean1);

    }

    @Test
    public void stringIncreTest() throws Exception {
        Long incre = null;
        for (int i = 0; i < 3; i++) {
            System.out.println(jedisHandler.get("3"));
            incre = jedisHandler.incre("3", 3);
        }

        System.out.println(incre);
        System.out.println(jedisHandler.get("3"));
        TimeUnit.SECONDS.sleep(4);
        System.out.println(jedisHandler.get("3"));
    }

    @Test
    public void TTLTest() throws Exception {
        String key = "nonKey";
        //key 不存在时 返回-2
        System.out.println(jedisHandler.ttl(key));
        key = "existKey";
        jedisHandler.set(key, "existValue");
        //key 永久存在 返回-1
        System.out.println(jedisHandler.ttl(key));
        //设置生效时间
        jedisHandler.set(key, "existValue", 3l);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(jedisHandler.ttl(key));
        TimeUnit.SECONDS.sleep(3);
        //已过期 返回 -2
        System.out.println(jedisHandler.ttl(key));
        System.out.println(jedisHandler.get(key));
    }

    @Test
    public void stringSetOffsetTest() throws Exception {
        String key = "nonKey";
        String originValue = "hello world";
        jedisHandler.set(key, originValue, 3l);
        //从originValue偏移位6开始赋值，实际值为hello redis
        String newValue = "redis";
        jedisHandler.set(key, newValue, 6);
        //打印：hello redis
        System.out.println(jedisHandler.get(key));
        TimeUnit.SECONDS.sleep(3);
        //已过期 null
        System.out.println(jedisHandler.get(key));

    }

    @Test
    public void stringSetIfAbsentTest() throws Exception {
        String key = "nonKey";
        String originValue = "hello world";
        Boolean aBoolean = jedisHandler.setIfAbsent(key, originValue, 3l);
        // set成功 打印：true
        System.out.println(aBoolean);
        //打印：hello world
        System.out.println(jedisHandler.get(key));
        String newValue = "new value";
        aBoolean = jedisHandler.setIfAbsent(key, newValue, 3l);
        //set 失败 打印：false
        System.out.println(aBoolean);
        //打印：hello world
        System.out.println(jedisHandler.get(key));
        TimeUnit.SECONDS.sleep(3);
        //已过期 打印：null
        System.out.println(jedisHandler.get(key));
        aBoolean = jedisHandler.setIfAbsent(key, originValue, 3l);
        // set成功 打印：true
        System.out.println(aBoolean);
        //打印：hello world
        System.out.println(jedisHandler.get(key));

    }


    @Test
    public void delTest() throws Exception {
        String key = "delKey";
        String originValue = "hello world";
        String newValue = "hello redis";

        jedisHandler.set(key, originValue);
        //hello world
        System.out.println(jedisHandler.get(key));
        //true
        System.out.println(jedisHandler.del(key));
        //null
        System.out.println(jedisHandler.get(key));
        //true
        System.out.println(jedisHandler.setIfAbsent(key, originValue));
        //hello world
        System.out.println(jedisHandler.get(key));
        //true
        System.out.println(jedisHandler.del(key));
        //null
        System.out.println(jedisHandler.get(key));
        //true
        System.out.println(jedisHandler.setIfAbsent(key, newValue));
        //hello redis
        System.out.println(jedisHandler.get(key));

    }

    @Test
    public void expireTest() throws Exception {
        String key = "delKey";
        String originValue = "hello world";
        Boolean expire = jedisHandler.expire(key, 3);
        //false
        System.out.println(expire);
        jedisHandler.set(key, originValue);
        expire = jedisHandler.expire(key, 3);
        //true
        System.out.println(expire);
        //hello world
        System.out.println(jedisHandler.get(key));
        TimeUnit.SECONDS.sleep(2);
        // 1
        System.out.println(jedisHandler.ttl(key));
        expire = jedisHandler.expire(key, 3);
        //true
        System.out.println(expire);
        // 3
        System.out.println(jedisHandler.ttl(key));

    }


    /***********======================================list==========================*/

    @Test
    public void listRangeAndRightPushTest() throws Exception {
        String key = "listKey";
        String value1 = "listValue1";
        String value2 = "listValue2";
        String value3 = "listValue3";

        List<String> range = jedisHandler.range(key, 0, 1);
        //0
        System.out.println(range.size());
        //存储值
        Long aLong = jedisHandler.rightPush(key, value1);
        range = jedisHandler.range(key, 0, -1);
        //1
        System.out.println(range.size());
        //listValue1
        range.stream().forEach(System.out::println);

        aLong = jedisHandler.rightPushAll(key, value2, value3);
        //3
        System.out.println(aLong);

        range = jedisHandler.range(key, 0, -1);
        //3
        System.out.println(range.size());
        //listValue1 listValue2 listValue3
        range.stream().forEach(System.out::println);
        //取第一个值
        range = jedisHandler.range(key, 0, 0);
        //listValue1
        range.stream().forEach(System.out::println);

        //listValue3
        System.out.println(jedisHandler.rightPop(key));
        //删除key
        jedisHandler.del(key);
    }




   /* @Test
    public void lettuceRedisTest() throws Exception{
        Boolean aBoolean = lettuceRedisHandler.acquireLock("lockKey", "lockValue", 3l);

        System.out.println(aBoolean);

        TimeUnit.SECONDS.sleep(4);

        Boolean aBoolean1 = lettuceRedisHandler.releaseLock("lockKey", "lockValue");
        System.out.println(aBoolean1);

    }
*/

}
