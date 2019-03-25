package com.hession.services.springdemo.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @program: hession-springdemo-service
 * @description: 自定义监听器案例
 *                todo 该类是监听servlet的，如果没有servlet类，该监听器是无效的。
 * @author: Hession
 * @create: 2019-03-03 15:25
 *
 *
 **/
@Slf4j
@WebListener//开启监听器注解
public class MyListener implements ServletContextListener {

    /**
     * 当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
     * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
      log.error("action=contextInitialized, this is a listener start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.error("action=contextDestroyed, this is a listener end");
    }
}
