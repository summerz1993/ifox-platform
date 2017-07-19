package com.ifox.platform.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * @author Yeager
 * 统一异常处理
 */
@Configuration
public class ExceptionResolverConfig {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 常见异常处理方法
     * @return SimpleMappingExceptionResolver
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        logger.info("初始化 --> 常见异常处理方法(simpleMappingExceptionResolver)");

        Properties exceptionProperties = new Properties();
        exceptionProperties.setProperty("NoHandlerFoundException", "404");

        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        exceptionResolver.setExceptionMappings(exceptionProperties);
        return exceptionResolver;
    }

}
