package com.hession.services.springdemo.aspect;

import com.hession.services.springdemo.entity.RequestEntity;
import com.hession.services.springdemo.entity.ResponseEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @program: hession-springdemo-service
 * @description: 切面类
 * @author: Hession
 * @create: 2019-03-18 21:34
 **/

@Aspect // 使用@Aspect注解声明一个切面
@Component
public class ControllerAspect {

  private final String EXECUTION ="execution(public * com.hession.services.springdemo.controller.RequestController.*(..))";

    @Pointcut("execution(public * com.hession.services.springdemo.controller.*.getBill(..))")
    public void billAspect() {

    }


    @Pointcut("execution(public * com.hession.services.springdemo.controller.RequestController.*(..))")
    public void requestAspect() {

    }

    @Around("billAspect()")
    /**
     * 处理请求参数类型为String类型时须用@Around
     * */
    public Object billControlAspect(JoinPoint joinPoint) throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
        /**获取参数值*/
        Object[] args = proceedingJoinPoint.getArgs();
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        /**获取参数名称*/
        String[] parameterNames = signature.getParameterNames();

        List<String> list = Arrays.asList("billName");
        for (int i = 0; i < parameterNames.length; i++) {
            if (list.contains(parameterNames[i])) {
                args[i] += "环绕切面包装";
            }
        }

        /**传入修改包装后的参数;因参数类型为不可变String类型而不是引用类型，所以必须使用环绕@Around通知传入*/
        return proceedingJoinPoint.proceed(args);
    }


    /**
     * 处理请求参数类型为引用类型时用@Before
     * */
    @Before("requestAspect()||billAspect()")
    public void requestControlAspect(JoinPoint joinPoint) {
        /**获取参数值*/
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof RequestEntity) {
                RequestEntity entity = (RequestEntity) arg;
                entity.setRequestName(entity.getRequestName() + "前置通知修改");
            }
        }
    }

    /**
     * 处理返回体用AfterReturning
     * */
    @AfterReturning(value = EXECUTION,returning = "object")
    public void responseAspect(Object object) {
        if (object instanceof ResponseEntity) {
            ResponseEntity responseEntity = (ResponseEntity) object;
            responseEntity.setName(responseEntity.getName() + "修改返回体数据");
        }
    }

}
