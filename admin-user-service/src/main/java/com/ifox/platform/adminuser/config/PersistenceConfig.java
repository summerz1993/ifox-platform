package com.ifox.platform.adminuser.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Yeager
 *
 * DAL相关配置
 */
@SuppressWarnings("unchecked")
@Configuration
@PropertySource({"classpath:persistence-mysql.properties"})
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
        sessionFactory.setPackagesToScan("com.ifox.platform.entity");
        //设置hibernate基本属性
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        //alibaba druid:https://github.com/alibaba/druid
        final DruidDataSource druidDataSource = new DruidDataSource();
        //Druid是根据url前缀来识别DriverClass, 所有更加简洁无需配置Driver
        druidDataSource.setUrl(env.getProperty("jdbc.url"));
        druidDataSource.setUsername(env.getProperty("jdbc.username"));
        druidDataSource.setPassword(env.getProperty("jdbc.password"));
        druidDataSource.setInitialSize(Integer.valueOf(env.getProperty("datasource.pool.initialSize")));
        druidDataSource.setMinIdle(Integer.valueOf(env.getProperty("datasource.pool.minIdle")));
        druidDataSource.setMaxActive(Integer.valueOf(env.getProperty("datasource.pool.maxActive")));
        druidDataSource.setMaxWait(Long.valueOf(env.getProperty("datasource.pool.maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("datasource.pool.timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("datasource.pool.minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(env.getProperty("datasource.pool.validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("datasource.pool.testWhileIdle")));

        druidDataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("datasource.pool.testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("datasource.pool.testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("datasource.pool.poolPreparedStatements")));

        return druidDataSource;
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

    private Properties hibernateProperties() {

        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("hibernate.generate_statistics"));
        hibernateProperties.setProperty("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size"));
        hibernateProperties.setProperty("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size"));
        hibernateProperties.setProperty("hibernate.max_fetch_depth", env.getProperty("hibernate.max_fetch_depth"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));
//		hibernateProperties.setProperty("hibernate.cache.provider_class",env.getProperty("hibernate.cache.provider_class"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

        return hibernateProperties;
    }

}
