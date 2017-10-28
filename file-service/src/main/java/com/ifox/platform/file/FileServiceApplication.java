package com.ifox.platform.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 文件服务
 */
@SpringBootApplication
@ComponentScan(value = {"com.ifox.platform.file"})
public class FileServiceApplication {

    private static Logger logger = LoggerFactory.getLogger(FileServiceApplication.class);

    public static void main(String[] args) {
        logger.info("FileService启动中");
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
