package com.ifox.platform.adminuser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
	
//	@Bean
//    public MessageSource messageSource() {
//    	ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasenames("bundle/common/MS1ResourceBundle");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
//
//	@Bean
//	public LocalValidatorFactoryBean validator() {
//	    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
//	    validatorFactoryBean.setValidationMessageSource(messageSource());
//	    return validatorFactoryBean;
//	}
//
//	@Override
//	public Validator getValidator() {
//	    return validator();
//	}
//
//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver getResolver() throws IOException {
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding("UTF-8");
//		return resolver;
//	}
//
//	@Override
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		registry.enableContentNegotiation(new MappingJackson2JsonView());
//		registry.jsp("/WEB-INF/view/", ".jsp");
//	}

}
