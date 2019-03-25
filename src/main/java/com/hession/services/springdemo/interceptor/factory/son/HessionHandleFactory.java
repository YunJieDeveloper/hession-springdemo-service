package com.hession.services.springdemo.interceptor.factory.son;

import com.hession.services.springdemo.interceptor.factory.AbstractHandleFactory;
import com.hession.services.springdemo.interceptor.handle.son.BillHandler;
import com.hession.services.springdemo.interceptor.handle.son.TaskHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * 具体业务实现
 * Ps. 此工程集合在了一起,
 * 实际生产环境可在具体的业务服务中实现,将此工程提取为公共模块使用
 */
@Component
public class HessionHandleFactory extends AbstractHandleFactory {

    @Autowired
    private TaskHandler taskHandler;
    @Autowired
    private BillHandler billHandler;

    @PostConstruct
    public void init() {
        serverTypes.put("hession-REQUEST", taskHandler);
        serverTypes.put("hession-BILL", billHandler);
    }
}
