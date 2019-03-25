package com.hession.services.springdemo.config;


import com.hession.services.springdemo.Filter.GZipFilter;
import com.hession.services.springdemo.Filter.ParamHandleFilter;
import com.hession.services.springdemo.Filter.RequestLimitFilter;
import com.hession.services.springdemo.handler.CommonHandler;
import com.hession.services.springdemo.handler.RequestLimitHandler;
import com.hession.services.springdemo.interceptor.CommonInterceptor;
import com.hession.services.springdemo.interceptor.SimpleInterceptor;
import com.hession.services.springdemo.interceptor.enums.EventTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 *
 * @author hession
 * 自定义配置类
 * <p>
 * Ps.
 * 继承WebMvcConfigurationSupport类,通过重写父类方法,自定义业务配置
 *
 * 注：WebMvcConfigurerAdapter的用法和WebMvcConfigurationSupport、WebMvcConfigurer一致，但不建议用，已被Springboot2.0、Spring5.0废弃
 */

@Configuration
public class ServerConfig extends WebMvcConfigurationSupport {

    @Autowired
    private CommonProperties commonProperties;

    @Autowired
    private CommonHandler commonHandler;

    @Autowired
    RequestLimitHandler requestLimitHandler;


    /**
     * 过滤器: 1 GZipFilter
     */
    @Bean
    @Order(1)
    public FilterRegistrationBean gZipFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        GZipFilter gZipFilter = new GZipFilter();
        registration.setFilter(gZipFilter);
        registration.addUrlPatterns("/hession/v1/bill/*","/hession/v1/request/*");
        registration.setName("gZipFilter");
        return registration;
    }

    /**
     * 过滤器: 2 RequestLimitFilter
     */
    @Bean
    @Order(2)
    public FilterRegistrationBean requestLimitFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        RequestLimitFilter requestLimitFilter = new RequestLimitFilter(requestLimitHandler);
        registration.setFilter(requestLimitFilter);
        //注意：/hession/v1/request/* 不能写成/*/request/*格式
        registration.addUrlPatterns("/hession/v1/request/*");
        registration.setName("requestLimitFilter");
        return registration;
    }


    /**
     * 过滤器: 3
     */
    @Bean
    @Order(3)
    public FilterRegistrationBean paramHandleFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        ParamHandleFilter paramHandleFilter = new ParamHandleFilter();
        registration.setFilter(paramHandleFilter);
        //注意：/hession/v1/request/* 不能写成/*/request/*格式
        registration.addUrlPatterns("/hession/v1/bill/*","/hession/v1/request/*");
        registration.setName("paramHandleFilter");
        return registration;
    }

    /**
     * Spring Boot 定制URL匹配规则
     * configurePathMatch(PathMatchConfigurer configurer)函数让开发人员可以根据需求定制URL路径的匹配规则
     *
     * @param configurer PathMatchConfigurer
     *                   <p>
     *                   PS. 解决通过restful请求方式传递诸如127.0.0.1:8080此类的参数时发生截断的问题
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                // configurer.setUseSuffixPatternMatch(false)表示设计人员希望系统对外暴露的URL不会识别和匹配.*后缀:设置是否是后缀模式匹配,如“/user”是否匹配/user.*,默认真即匹配；
                .setUseSuffixPatternMatch(false)
                // configurer.setUseTrailingSlashMatch(true)表示系统不区分URL的最后一个字符是否是斜杠/:设置是否自动后缀路径模式匹配,如“/user”是否匹配“/user/”,默认真即匹配
                .setUseTrailingSlashMatch(true);
    }

    /**
     * 解决访问swagger-ui.html 404问题
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链 addPathPatterns 用于添加拦截规则 excludePathPatterns 用户排除拦截
        //PathPatterns格式可以是/hession/*/bill/*和/hession/v1/bill/*
        registry.addInterceptor(new CommonInterceptor(EventTypeEnum.REQUEST, commonProperties.getServerType(),commonHandler )).addPathPatterns(commonProperties.getTaskInterceptorPath());
        registry.addInterceptor(new CommonInterceptor(EventTypeEnum.BILL, commonProperties.getServerType(),commonHandler)).addPathPatterns(commonProperties.getBillInterceptorPath());
        //最简单的实现
        registry.addInterceptor(new SimpleInterceptor()).addPathPatterns(commonProperties.getSimpleInterceptorPath());

        super.addInterceptors(registry);
    }
}
