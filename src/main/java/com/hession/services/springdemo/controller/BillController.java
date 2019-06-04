package com.hession.services.springdemo.controller;

import com.hession.services.springdemo.aspect.annotation.ToAspect;
import com.hession.services.springdemo.common.ServerConstants;
import com.hession.services.springdemo.entity.RequestEntity;
import com.hession.services.springdemo.entity.ResponseEntity;
import com.hession.services.springdemo.exception.HessionServerException;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author  hession
 * 简单的demoController
 *
 * */

@RestController
@Api("BillController相关api")
@RequestMapping(ServerConstants.BASE_PATH + "/" + ServerConstants.VERSION)
public class BillController {


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true,
                    value = "令牌", defaultValue = "afddeeg12daaf32")})
    @ApiOperation(value = "getBill test")
    @RequestMapping(value = "/bill/getBill", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "请求未通过认证.", response = HessionServerException.class)})
    @ToAspect()
    public ArrayList<String> getBill(@RequestHeader("token") String token, @RequestParam("billName") String billName) {
        return new ArrayList<>(Arrays.asList(new String[]{billName}));
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true,
                    value = "令牌", defaultValue = "afddeeg12daaf32")})
    @ApiOperation(value = "getBill test")
    @RequestMapping(value = "/bill/getInfo", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "请求未通过认证.", response = HessionServerException.class)})
    public ResponseEntity getInfo(@RequestHeader("token") String token, @RequestParam("mobile") String mobile) {
        return  ResponseEntity.builder().mobile(mobile).build();
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true,
                    value = "令牌", defaultValue = "afddeeg12daaf32")})
    @ApiOperation(value = "getBill test")
    @RequestMapping(value = "/bill/getInfo", method = RequestMethod.POST)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "请求未通过认证.", response = HessionServerException.class)})
    public ResponseEntity getPostInfo(@RequestHeader("token") String token, @RequestBody RequestEntity requestEntity) {
        return  ResponseEntity.builder().mobile(requestEntity.getMobile()).name(requestEntity.getRequestName()).build();
    }

}
