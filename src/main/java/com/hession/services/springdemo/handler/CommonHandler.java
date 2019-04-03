package com.hession.services.springdemo.handler;


import com.hession.services.springdemo.interceptor.enums.EventTypeEnum;
import com.hession.services.springdemo.interceptor.factory.AbstractHandleFactory;
import com.hession.services.springdemo.interceptor.handle.BaseCommonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hession
 *
 * 该类的作用是做一个转接,接入具体的拦截器业务处理器
 * 特别注意：
 *    1、这里注入的的工厂类AbstractHandleFactory是抽象类，所以必须要有具体工厂实现子类(根据具体业务实现)。
 *       例：son包下的HessionHandleFactory;
 *    2、CommonHandler类必须通过构造函数参数的方式注入拦截器CommonInterceptor中，
 *       原理：CommonHandler类和AbstractHandleFactory类都是项目启动的时候spring框架自动注入的，
 *       优先级高于new CommonInterceptor()。所以在CommonInterceptor类中不能通过@Autowired的方式
 *       注入CommonHandler类和AbstractHandleFactory类
 *
 * */

@Component
public class CommonHandler {


    /***
     * 此类是抽象父类 必须要有子类实例才能注入
     * Ps. 见子类 HessionHandleFactory
     * */
    @Autowired
    private AbstractHandleFactory factory;

    public void preExecute(EventTypeEnum eventTypeEnum, String serverType, HttpServletRequest request, HttpServletResponse response, Object handler) {
        BaseCommonHandler commonHandler = this.getBaseCommonHandler(eventTypeEnum, serverType);
        commonHandler.preExecute(request,response,handler);
    }

    public void postHandle(EventTypeEnum eventTypeEnum, String serverType,HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BaseCommonHandler commonHandler = this.getBaseCommonHandler(eventTypeEnum, serverType);
        commonHandler.postExecute(request, response, handler);
    }

    public void afterCompletion(EventTypeEnum eventTypeEnum, String serverType,HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        BaseCommonHandler commonHandler = this.getBaseCommonHandler(eventTypeEnum, serverType);
        commonHandler.afterExecute(request, response, handler);
    }

    private BaseCommonHandler getBaseCommonHandler(EventTypeEnum eventTypeEnum, String serverType) {
        return factory.getHandler(String.format("%s-%s", serverType, eventTypeEnum.name()));
    }



}
