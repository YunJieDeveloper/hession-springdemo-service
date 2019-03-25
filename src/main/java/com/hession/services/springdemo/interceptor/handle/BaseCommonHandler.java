package com.hession.services.springdemo.interceptor.handle;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhanghesheng
 * 拦截器具体业务处理接口
 * Ps.支持根据业务逻辑扩展不同的拦截器规则
 */
public interface BaseCommonHandler {

    void preExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);

    void postExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);


    void afterExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);

}
