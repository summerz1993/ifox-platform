package com.ifox.platform.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 邮件服务
 * @author Yeager
 */
@SpringBootApplication
@ComponentScan(value = {"com.ifox.platform.email"})
public class EmailServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceApplication.class);

    public static void main(String[] args) {
        logger.info("email-service 项目启动中");
        SpringApplication.run(EmailServiceApplication.class, args);
    }

}
