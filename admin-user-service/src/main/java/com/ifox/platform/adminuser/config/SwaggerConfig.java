package com.ifox.platform.adminuser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger2配置
 * Created by yezhang on 7/13/2017.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(env.getProperty("swagger2.apis.base.package")))
//                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
//                .pathMapping("admin-user-api");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("Spring Boot + Swagger2")
                .contact("Yeager")
                .version("1.0")
                .build();
    }

}
