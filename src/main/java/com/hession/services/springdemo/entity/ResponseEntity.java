package com.hession.services.springdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: hession-springdemo-service
 * @description: 返回体类
 * @author: Hession
 * @create: 2019-03-20 22:31
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseEntity {

    String name;

    String mobile;

    String notes;
}
