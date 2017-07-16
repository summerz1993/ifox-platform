package com.ifox.platform.common.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Locale;

public class SpringContext implements ApplicationContextAware {

	private static ApplicationContext context;	
	
	public SpringContext() {}
		
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.context = applicationContext;
	}
	
	 public static ApplicationContext getApplicationContext() {		 
		    return context;
	 }
	 
	 public static String getProperty(String key){
		 return context.getEnvironment().getProperty(key);
	 }
	 
	 public static String getProperty(String key,String defaultValue){
		 return context.getEnvironment().getProperty(key,defaultValue);
	 }
	 
	 public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale){
		 return context.getMessage(code, args, defaultMessage, locale);
	 }
}
