package com.hession.services.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class HessionSpringdemoServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HessionSpringdemoServiceApplication.class, args);

    }

}
