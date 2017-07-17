package com.ifox.platform.utility.dao;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 数据源工具类
 * @author Yeager
 */
public class DataSourceUtil {

    /**
     * 创建数据源
     * @param env Spring环境变量
     * @return DataSource
     */
    public static DataSource createDataSource(Environment env){
        DruidDataSource druidDataSource = new DruidDataSource();
        //Druid是根据url前缀来识别DriverClass, 所有更加简洁无需配置Driver
        druidDataSource.setUrl(env.getProperty("app.datasource.jdbc.url"));
        druidDataSource.setUsername(env.getProperty("app.datasource.jdbc.username"));
        druidDataSource.setPassword(env.getProperty("app.datasource.jdbc.password"));
        druidDataSource.setInitialSize(Integer.valueOf(env.getProperty("app.datasource.pool.initialSize")));
        druidDataSource.setMinIdle(Integer.valueOf(env.getProperty("app.datasource.pool.minIdle")));
        druidDataSource.setMaxActive(Integer.valueOf(env.getProperty("app.datasource.pool.maxActive")));
        druidDataSource.setMaxWait(Long.valueOf(env.getProperty("app.datasource.pool.maxWait")));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getProperty("app.datasource.pool.timeBetweenEvictionRunsMillis")));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getProperty("app.datasource.pool.minEvictableIdleTimeMillis")));
        druidDataSource.setValidationQuery(env.getProperty("app.datasource.pool.validationQuery"));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("app.datasource.pool.testWhileIdle")));

        druidDataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("app.datasource.pool.testOnBorrow")));
        druidDataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("app.datasource.pool.testOnReturn")));
        druidDataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("app.datasource.pool.poolPreparedStatements")));

        return druidDataSource;
    }

}
