package com.hession.services.springdemo.exception.message;

import com.hession.services.springdemo.exception.message.ErrorMessage;
import lombok.ToString;

import java.net.URI;

/**
 * @program: hession-springdemo-service
 * @description: 具体项目的自定义异常信息包装类
 * @author: Hession
 * @create: 2019-04-03 21:59
 **/

@ToString
public class HessionServerErrorMessage extends ErrorMessage {
    private static final URI DEFAULT_TYPE = URI.create("uri:blank");

    public HessionServerErrorMessage() {
        this.setType(DEFAULT_TYPE);
    }

    public HessionServerErrorMessage(String title,Integer statusCode) {
        this.setType(DEFAULT_TYPE);
        this.setStatusCode(statusCode);
        this.setTitle(title);
    }

    public HessionServerErrorMessage(String title,Integer statusCode,String detailInfo) {
        this.setType(DEFAULT_TYPE);
        this.setStatusCode(statusCode);
        this.setDetailInfo(detailInfo);
        this.setTitle(title);
    }

    public HessionServerErrorMessage(String title,Integer statusCode,String detailInfo,URI instance) {
        this.setType(DEFAULT_TYPE);
        this.setStatusCode(statusCode);
        this.setDetailInfo(detailInfo);
        this.setTitle(title);
        this.setInstance(instance);
    }

}
