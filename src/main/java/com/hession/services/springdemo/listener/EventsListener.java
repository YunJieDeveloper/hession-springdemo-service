package com.hession.services.springdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @program: hession-springdemo-service
 * @description: 事件监听器案例：
 *                  监听spring各个事件
 * @author: hession
 * @create: 2019-03-03 15:07
 *
 * 在Spring Boot2.0版本中所有的事件按执行的先后顺序如下：
 *
 * ApplicationStartingEvent
 * ApplicationEnvironmentPreparedEvent
 * ApplicationPreparedEvent
 * ApplicationStartedEvent <= 新增的事件
 * ApplicationReadyEvent
 * ApplicationFailedEvent
 *
 *
 *
 *
 *
 *
 **/
@Component
@Slf4j
public class EventsListener {

    /**
     * @Author hession
     * @Description //TODO
     * @Date 15:17 2019-03-03
     * @Param readyEvent
     * @return
     */
    @EventListener(ApplicationReadyEvent.class)
    public void handleApplicationReadyEvent(ApplicationReadyEvent readyEvent){
      log.info("action=readyEvent,");
    }

}
