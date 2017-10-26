package com.ifox.platform.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 启动类
 * Created by yezhang
 */
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan({"com.ifox.platform"})
public class SystemServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(SystemServiceApplication.class);

    public static void main(String[] args) {
        logger.info("system-service 项目启动中");
        SpringApplication.run(SystemServiceApplication.class, args);
    }

}
