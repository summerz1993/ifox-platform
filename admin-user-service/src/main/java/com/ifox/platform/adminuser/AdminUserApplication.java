package com.ifox.platform.adminuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * Created by yezhang
 */
@SpringBootApplication
@ComponentScan({"com.ifox.platform"})
public class AdminUserApplication {

    static Logger logger = LoggerFactory.getLogger(AdminUserApplication.class);

    public static void main(String[] args) {
        logger.info("admin-user-service 项目启动中");
        SpringApplication.run(AdminUserApplication.class, args);
    }

}
