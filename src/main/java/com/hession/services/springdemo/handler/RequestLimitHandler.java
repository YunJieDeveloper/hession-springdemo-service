package com.hession.services.springdemo.handler;

import com.hession.services.springdemo.handler.redis.JedisHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hession
 * 请求限流具体处理类
 * */

@Service
public class RequestLimitHandler {

    private  static  final Pattern REQUEST_URL=Pattern.compile(".*/request/.*");
    private  static  final String RECORD_REQUEST_KEY="service:request:record" ;
    private  static  final int EXPIRE_KEY_TIME=10;
    private  static  final int EXPIRE_REQUEST_COUNT=10000;


    @Autowired
   private JedisHandler jedisHandler;

    public void recordRequestCount(String uri) throws Exception{
        Matcher matcher = REQUEST_URL.matcher(uri);
        if (matcher.find()){
            Long requestCount = jedisHandler.incre(RECORD_REQUEST_KEY, EXPIRE_KEY_TIME);
             //todo 超过请求数 抛异常出去,建议自定义异常,优化提示语
            if (requestCount>EXPIRE_REQUEST_COUNT){
                throw new Exception("too many request, please retry later");
            }
        }

    }
}
