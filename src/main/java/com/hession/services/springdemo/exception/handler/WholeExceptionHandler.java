package com.hession.services.springdemo.exception.handler;

import com.hession.services.springdemo.exception.HessionServerException;
import com.hession.services.springdemo.exception.message.HessionServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

/**
 * @program: hession-springdemo-service
 * @description: 统一异常处理类
 * @author: Hession
 * @create: 2019-04-03 22:15
 **/
@ControllerAdvice
public class WholeExceptionHandler {


    /**
     * @param
     * @return
     * @author hession
     * @description 处理 RuntimeException
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HessionServerErrorMessage handleRuntimeError(RuntimeException e, HttpServletRequest request) {
        HessionServerErrorMessage errorMessage = new HessionServerErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.name(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常,请稍后再试!");
        errorMessage.setInstance(URI.create(request.getRequestURI()));
        return errorMessage;
    }


    /**
     * @param
     * @return
     * @author hession
     * @description 处理自定义异常 HessionServerException
     */
    @ExceptionHandler(HessionServerException.class)
    @ResponseBody
    public HessionServerErrorMessage handleHessionServerError(HessionServerException e, HttpServletRequest request) {
        HessionServerErrorMessage errorMessage = e.getErrorMessage();
        errorMessage.setInstance(errorMessage.getInstance() == null ? URI.create(request.getRequestURI()) : errorMessage.getInstance());
        return errorMessage;
    }


}
