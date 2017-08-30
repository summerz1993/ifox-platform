package com.ifox.platform.email;

import com.ifox.platform.baseservice.config.PersistenceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 邮件服务
 * @author Yeager
 */
@SpringBootApplication
@ComponentScan(value = {"com.ifox.platform.email", "com.ifox.platform.baseservice.interceptor", "com.ifox.platform.baseservice.config"},
    excludeFilters = {@ComponentScan.Filter(value = PersistenceConfig.class, type = FilterType.ASSIGNABLE_TYPE)})
public class EmailServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceApplication.class);

    public static void main(String[] args) {
        logger.info("email-service 项目启动中");
        SpringApplication.run(EmailServiceApplication.class, args);
    }

}
