package com.hession.services.springdemo.controller;

import com.hession.services.springdemo.common.ServerConstants;
import com.hession.services.springdemo.entity.RequestEntity;
import com.hession.services.springdemo.entity.ResponseEntity;
import com.hession.services.springdemo.exception.HessionServerException;
import com.hession.services.springdemo.exception.message.HessionServerErrorMessage;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author hession
 * 简单的demoController
 */
@Slf4j
@RestController
@Api("RequsetController相关api")
@RequestMapping(ServerConstants.BASE_PATH + "/" + ServerConstants.VERSION)
public class RequestController {


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true,
                    value = "令牌", defaultValue = "afddeeg12daaf32")})
    @ApiOperation(value = "sayHi test")
    @RequestMapping(value = "/request/hello", method = RequestMethod.GET)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "请求未通过认证.", response = HessionServerException.class)})
    public ResponseEntity sayHi(@RequestHeader("token") String token, @RequestParam("name") String name) {
        log.info("action=sayHi");
        return ResponseEntity.builder().name(name).build();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "token", dataType = "String", required = true,
                    value = "令牌", defaultValue = "afddeeg12daaf32")})
    @ApiOperation(value = "sayHi post test")
    @RequestMapping(value = "/request/hello", method = RequestMethod.POST)
    @ApiResponses(value = {@ApiResponse(code = 401, message = "请求未通过认证.", response = HessionServerException.class)})
    public ResponseEntity sayHi(@RequestHeader("token") String token, @RequestBody RequestEntity requestEntity) throws HessionServerException {
        log.info("action=sayHi,post");
         throw new HessionServerException(new HessionServerErrorMessage("sayHi occur error", 403, "bad request"));
       // return ResponseEntity.builder().name(requestEntity.getRequestName()).build();
    }


}
