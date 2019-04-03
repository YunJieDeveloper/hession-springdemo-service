package com.hession.services.springdemo.handler.redis;

//import io.lettuce.core.SetArgs;
//import io.lettuce.core.api.async.RedisAsyncCommands;
//import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @program: hession-springdemo-service
 * @description: 使用Lettuce客户端
 * @author: Hession
 * @create: 2019-03-30 15:53
 *
 *redis操作具体处理类
 *todo 本工程使用Jedis客户端，因此暂时注释掉加锁方法acquireLock();
 */

@Component
public class LettuceRedisHandler extends RedisBaseHandler {

/**
     * @param key key值
     * @param value value值
     * @param expiredTime 过期时间（秒）
     * @return
*/


   /* public boolean acquireLock(String key, String value, long expiredTime) {
        Boolean resultBoolean = null;
        try {
            resultBoolean = stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
                Object nativeConnection = connection.getNativeConnection();
                String redisResult = "";
                @SuppressWarnings("unchecked")
                RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) stringRedisTemplate.getKeySerializer();
                //lettuce连接包下序列化键值，否知无法用默认的ByteArrayCodec解析
                byte[] keyByte = stringRedisSerializer.serialize(key);
                byte[] valueByte = stringRedisSerializer.serialize(value);
                // lettuce连接包下 redis 单机模式setnx
                if (nativeConnection instanceof RedisAsyncCommands) {
                    RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
                    //同步方法执行、setnx禁止异步
                    redisResult = commands
                            .getStatefulConnection()
                            .sync()
                                                 //ex = seconds, px = milliseconds
                            .set(keyByte, valueByte, SetArgs.Builder.nx().ex(expiredTime));
                }
                // lettuce连接包下 redis 集群模式setnx
                if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                    RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                    redisResult = clusterAsyncCommands
                            .getStatefulConnection()
                            .sync()
                                              //ex = seconds, px = milliseconds
                            .set(keyByte, keyByte, SetArgs.Builder.nx().ex(expiredTime));
                }
                //返回加锁结果
                return "OK".equalsIgnoreCase(redisResult);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBoolean != null && resultBoolean;
    }
*/

}
