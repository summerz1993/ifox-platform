package com.ifox.platform.adminuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * Created by yezhang
 */
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan({"com.ifox.platform.adminuser", "com.ifox.platform.baseservice", "com.ifox.platform.common", "com.ifox.platform.dao",
                "com.ifox.platform.entity", "com.ifox.platform.utility"})
public class AdminUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminUserApplication.class, args);
    }

}
