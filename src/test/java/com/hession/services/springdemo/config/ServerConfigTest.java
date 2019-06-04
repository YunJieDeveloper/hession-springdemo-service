package com.hession.services.springdemo.config;

import com.hession.services.springdemo.BaseSpringbootTests;
import com.hession.services.springdemo.entity.DemoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ServerConfigTest extends BaseSpringbootTests {

    @Autowired
   private DemoEntity demoEntity;


    @Test
    public void getDemoEntity() {
        System.out.println(demoEntity);
    }
}