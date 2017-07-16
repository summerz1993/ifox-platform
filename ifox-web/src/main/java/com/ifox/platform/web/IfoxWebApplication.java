package com.ifox.platform.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * Created by yezhang on 7/13/2017.
 */
@SpringBootApplication
@ComponentScan("com.ifox.platform.web")
public class IfoxWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IfoxWebApplication.class, args);
    }

}
