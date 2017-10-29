package com.ifox.platform.file.config;

import com.ifox.platform.file.interceptor.OptionsRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Yeager
 *
 * WebMVC配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 设置由 web容器处理静态资源 ，相当于 xml中的<mvc:default-servlet-handler/>
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/pub-resource/**").addResourceLocations("/resource/");

		// Swagger UI resource handlers
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

    /**
     * 允许任何请求跨域访问
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE");
    }

    @Bean
    public OptionsRequestInterceptor authenticationInterceptor(){
        return new OptionsRequestInterceptor();
    }

    /**
     * 配置拦截器
     * @param registry 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/error", "/swagger-resources/**", "/file/get");
        super.addInterceptors(registry);
    }
}
