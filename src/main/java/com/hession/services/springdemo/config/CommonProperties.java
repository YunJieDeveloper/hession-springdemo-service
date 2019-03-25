package com.hession.services.springdemo.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.properties 配置类
 *
 * @author hession
 */
@Component
@ConfigurationProperties(prefix = "hession.gateway.common")
@Data
public class CommonProperties {

    /**
     * application.pro中取不到，就用Value中的默认值
     */
    @Value("/hession/v1/request/*")
    private String taskInterceptorPath;
    @Value("${billInterceptorPath:/hession/v1/bill/*}")
    private String billInterceptorPath;

    @Value("${hession.gateway.common.simple-interceptor-path:/hession/v1/simple/**}")
    private String simpleInterceptorPath;

    private String serverType;


}
