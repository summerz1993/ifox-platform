package com.ifox.platform.adminuser.config;

import com.ifox.platform.utility.dao.DataSourceUtil;
import com.ifox.platform.utility.dao.HibernatePropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author Yeager
 *
 * DAL相关配置
 */
@SuppressWarnings("unchecked")
@Configuration
//@PropertySource({"classpath:persistence-mysql.properties"})
public class PersistenceConfig {

    /**
     * 上面导入的属性文件中的属性会 注入到 Environment 中
     */
    private final Environment env;

    @Autowired
    public PersistenceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        //配置数据源
        sessionFactory.setDataSource(dataSource());
        //配置entity扫描包路径
        sessionFactory.setPackagesToScan(env.getProperty("sessionFactory.package.scan"));
        //设置hibernate基本属性
        sessionFactory.setHibernateProperties(HibernatePropertiesUtil.hibernateProperties(env));
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceUtil.createDataSource(env);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
