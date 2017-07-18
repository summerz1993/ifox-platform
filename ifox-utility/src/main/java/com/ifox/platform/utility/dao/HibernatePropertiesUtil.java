package com.ifox.platform.utility.dao;

import org.springframework.core.env.Environment;

import java.util.Properties;

/**
 * Hibernate参数设置
 * @author Yeager
 */
public class HibernatePropertiesUtil {

    /**
     * 获取hibernate参数
     * @param env 系统环境
     * @return Properties
     */
    public static Properties hibernateProperties(Environment env) {

        Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.max_fetch_depth", env.getProperty("hibernate.max_fetch_depth"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
//		hibernateProperties.setProperty("hibernate.cache.provider_class",env.getProperty("hibernate.cache.provider_class"));

//        hibernateProperties.setProperty("hibernate.implicit_naming_strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyHbmImpl");
//        hibernateProperties.setProperty("hibernate.physical_naming_strategy", "com.ifox.platform.utility.dao.ImprovedNamingStrategy");
//        hibernateProperties.setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");

        return hibernateProperties;
    }

}
