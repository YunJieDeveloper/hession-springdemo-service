package com.hession.services.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: hession-springdemo-service
 * @description: 通过自定义注解实现切面
 * @author: Hession
 * @create: 2019-06-04 23:52
 **/
@Aspect
@Component//注入bean
public class AnnotionAspect {


    @Pointcut("@annotation(com.hession.services.springdemo.aspect.annotation.ToAspect)")
    public void ToAspect() {
    }


    @Around("ToAspect()")
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
                //此处是对入参做切面处理
                args[i] += ":环绕切面包装";
                //此处可做切面方法操作
                if (false) {
                    ArrayList<String> list1 = new ArrayList<>();
                    list1.add("测试切面");
                    return list1;
                }
            }
        }

        /**传入修改包装后的参数;因参数类型为不可变String类型而不是引用类型，所以必须使用环绕@Around通知传入*/
        return proceedingJoinPoint.proceed(args);
    }




}
