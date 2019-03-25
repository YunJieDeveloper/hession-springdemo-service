package com.hession.services.springdemo.interceptor.handle.son;

import com.hession.services.springdemo.interceptor.handle.BaseCommonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author hession
 * 拦截器具体业务实现类
 * 本案例项目 为了方便举例直接通过son包做了具体业务类拦截逻辑的实现，
 * 实际业务场景中，可以在具体的业务项目中通过依赖项目jar包，继承的方式实现
 */
@Service
@Slf4j
public class TaskHandler implements BaseCommonHandler {

    @Override
    public void preExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
      //拦截操作
        log.info("action=preExcute, class:[{}]" ,TaskHandler.class.getName());

    }

    @Override
    public void postExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        //拦截操作
        log.info("action=postExcute, class:[{}]" ,TaskHandler.class.getName());

    }

    @Override
    public void afterExecute(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
      //拦截操作
        log.info("action=afterExcute, class:[{}]" ,TaskHandler.class.getName());

    }
}
