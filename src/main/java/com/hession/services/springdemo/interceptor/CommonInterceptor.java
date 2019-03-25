package com.hession.services.springdemo.interceptor;

import com.hession.services.springdemo.interceptor.enums.EventTypeEnum;
import com.hession.services.springdemo.handler.CommonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hession
 *
 * @description 自定义拦截器 必须实现接口HandlerInterceptor
 *              模板例子：new CommonInterceptor(CommonHandler commonHandler)  -->  CommonHandler   -->  AbstractHandleFactory(抽象类)   -->   BaseCommonHandler(接口)
 *                                                                                                      |                                      |
 *                                                                                                      |-->HessionHandleFactory               |-->BillHandler
 *                                                                                                                                             |-->TaskHandler
 *
 *
 *
 * */
@Slf4j
public class CommonInterceptor implements HandlerInterceptor {

    private  EventTypeEnum eventTypeEnum;
    private  String  serverType;
    private CommonHandler commonHandler;


    public CommonInterceptor(EventTypeEnum typeEnum, String serverType, CommonHandler commonHandler) {
        this.eventTypeEnum=typeEnum;
        this.serverType=serverType;
        this.commonHandler=commonHandler;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("action=preHandle,CommonInterceptor");
        commonHandler.preExecute(eventTypeEnum,serverType,request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        log.info("action=postHandle,CommonInterceptor");
        commonHandler.postHandle(eventTypeEnum,serverType,request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("action=afterCompletion,CommonInterceptor");
        commonHandler.afterCompletion(eventTypeEnum,serverType,request, response, handler);
    }




}
