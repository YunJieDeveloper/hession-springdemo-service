package com.hession.services.springdemo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hession
 *
 * @description 自定义拦截器最简单的实现方式：
 * 1、implements接口HandlerInterceptor；
 * 2、创建配置类ServerConfig 继承WebMvcConfigurationSupport,
 *    重写方法addInterceptors(InterceptorRegistry registry)
 *
 *
 *
 *
 * */
@Slf4j
public class SimpleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("action=preHandle,SimpleInterceptor");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("action=postHandle,SimpleInterceptor");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("action=afterCompletion,SimpleInterceptor");

    }
}
