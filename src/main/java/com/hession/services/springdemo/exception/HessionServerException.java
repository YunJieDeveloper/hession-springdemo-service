package com.hession.services.springdemo.exception;

import com.hession.services.springdemo.exception.message.HessionServerErrorMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: hession-springdemo-service
 * @description: 自定义异常类
 * @author: Hession
 * @create: 2019-04-03 22:19
 **/
public class HessionServerException extends Exception {

    @Getter
    @Setter
    private HessionServerErrorMessage errorMessage;

    public HessionServerException(HessionServerErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HessionServerException(HessionServerErrorMessage errorMessage, Throwable throwable) {
        super(throwable);
        this.errorMessage = errorMessage;
    }

    public HessionServerException(Throwable throwable) {
        super(throwable);
    }
}
