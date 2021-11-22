package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author :nier
 * @description:
 * @date 2019/10/23 0023 10:38
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class application {
    public static void main(String[] args) {
        SpringApplication.run(application.class,args);
    }
}
