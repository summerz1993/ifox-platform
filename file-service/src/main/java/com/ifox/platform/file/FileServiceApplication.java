package com.ifox.platform.file;

import com.ifox.platform.baseservice.config.PersistenceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 文件服务
 */
@SpringBootApplication
@ComponentScan(value = {"com.ifox.platform.file", "com.ifox.platform.baseservice.interceptor", "com.ifox.platform.baseservice.config"},
    excludeFilters = {@ComponentScan.Filter(value = PersistenceConfig.class, type = FilterType.ASSIGNABLE_TYPE)})
public class FileServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(FileServiceApplication.class);

    public static void main(String[] args) {
        logger.info("FileService启动中");
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
