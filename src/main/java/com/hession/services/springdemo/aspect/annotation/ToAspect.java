package com.hession.services.springdemo.aspect.annotation;


import java.lang.annotation.*;

// 声明注解的保留期限
@Retention(RetentionPolicy.RUNTIME)
// 声明可以使用该注解的目标类型
@Target(ElementType.METHOD)
@Documented
public @interface ToAspect {
    boolean value() default false;
}
