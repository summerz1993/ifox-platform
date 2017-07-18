package com.ifox.platform.utility.dao;

import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

/**
 * SessionFactory工具类
 * @author Yeager
 */
public class SessionFactoryUtil {

    /**
     * 创建SessionFactory工具类
     * @param env 环境变量
     * @return LocalSessionFactoryBean
     */
    public static LocalSessionFactoryBean createSessionFactory(Environment env) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        //配置数据源
        sessionFactory.setDataSource(DataSourceUtil.createDataSource(env));
        //配置entity扫描包路径
        sessionFactory.setPackagesToScan(env.getProperty("sessionFactory.package.scan"));
        //设置hibernate基本属性
        sessionFactory.setHibernateProperties(HibernatePropertiesUtil.hibernateProperties(env));
        return sessionFactory;
    }

}
