package com.hession.services.springdemo.exception.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.net.URI;

/**
 * @program: hession-springdemo-service
 * @description: 异常信息包装类，对异常统一处理
 * @author: Hession
 * @create: 2019-04-03 21:47
 **/
@Data
@EqualsAndHashCode
@ToString
public class ErrorMessage {
    private URI type;
    private URI instance;
    private String title;
    @JsonProperty("status_code")
    private Integer statusCode;
    @JsonProperty("detail_info")
    private String detailInfo;


}
