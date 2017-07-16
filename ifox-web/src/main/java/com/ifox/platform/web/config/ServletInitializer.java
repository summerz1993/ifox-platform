/**
 * 
 */
package com.ifox.platform.web.config;

import com.ifox.platform.web.IfoxWebApplication;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;


/**
 * @author yezhang
 *
 * Servlet 3容器的初始化
 */
public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer{

	/**
	 * 配置Spring的应用上下文
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { IfoxWebApplication.class };
	}

	/**
	 * 配置Spring MVC
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebMvcConfig.class};
	}

	/**
	 * DispatcherServlet Mapping路径
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/ifox-web/*" };
	}

	/**
	 * 启动配置
	 * @param servletContext servlet上下文
	 * @throws ServletException servlet异常
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);

		//配置编码过滤器
		FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
		encodingFilter.setInitParameter("forceEncoding", "true");
		encodingFilter.addMappingForUrlPatterns(null, false, "/ifox-web/*");



	}

}
