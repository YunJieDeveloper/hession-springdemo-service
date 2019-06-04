package com.hession.services.springdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @program: hession-springdemo-service
 * @description: 接口请求耗时
 * @author: Hession
 * @create: 2019-05-28 00:37
 **/
@Aspect
@Order(2)
@Component
@Slf4j
public class ResponseTimeLogAspect {
    private final static String EXECUTION = "execution(public * com.hession.services.springdemo.controller.RequestController.*(..))||execution(public * com.hession.services.springdemo.controller.BillController.*(..))";

    private static ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before(EXECUTION)
    public void init() {
        startTime.set(System.currentTimeMillis());
    }


    @AfterReturning(EXECUTION)
    public void afterReturn(JoinPoint joinPoint) {
        log.info("Method:[{}]->Params:[{}]->responseTime:{}ms",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                System.currentTimeMillis()-startTime.get()
        );
        startTime.remove();
    }
}
