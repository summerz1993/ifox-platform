package com.ifox.platform.adminuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * Created by yezhang
 */
@SpringBootApplication
@ComponentScan({"com.ifox.platform"})
public class SystemServiceApplication {

    static Logger logger = LoggerFactory.getLogger(SystemServiceApplication.class);

    public static void main(String[] args) {
        logger.info("system-service 项目启动中");
        SpringApplication.run(SystemServiceApplication.class, args);
    }

}
