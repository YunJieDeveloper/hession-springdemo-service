package com.hession.services.springdemo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @program: hession-springdemo-service
 * @description: 请求参数类
 * @author: Hession
 * @create: 2019-03-19 23:26
 **/
@Data
public class RequestEntity {

    @JsonProperty("request_name")
    String requestName;

    @JsonProperty("mobile")
    String mobile;
}
