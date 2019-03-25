package com.hession.services.springdemo.interceptor.factory;


import com.hession.services.springdemo.interceptor.handle.BaseCommonHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hession
 * 抽象一个工厂类, 业务可根据不同业务url自定义子工厂类继承该类添加需要拦截的业务类型
 *
 * **/

public abstract class AbstractHandleFactory {
    /**
     * 存放业务类型Map
     * */
    protected Map<String, BaseCommonHandler> serverTypes= new HashMap<>();

    public BaseCommonHandler getHandler(String type){
        return serverTypes.get(type);
    }

}
