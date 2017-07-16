package com.ifox.platform.common.bean;

import com.ifox.platform.common.exception.ConstructorException;

/**
 * @author Yeager
 *
 * 查询条件集合
 */
public class QueryConditions {

    /**
     * 查询的字段
     * 不能为空，为了规范查询结果
     * 不能查询集合属性
     */
    private String[] properties;

    /**
     * 查询条件
     */
    private QueryProperty[] queryProperties;

    /**
     * 排序条件
     */
    private SimpleOrder[] simpleOrders;

    /**
     * 构造方法，必须指定查询的字段
     * @param properties 查询的字段
     */
    public QueryConditions(String[] properties) {
        if (properties == null || properties.length == 0){
            throw new ConstructorException("QueryConditions class 初始化构造方法异常, 必须传入propertys");
        }
        this.properties = properties;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }


    public SimpleOrder[] getSimpleOrders() {
        return simpleOrders;
    }

    public void setSimpleOrders(SimpleOrder[] simpleOrders) {
        this.simpleOrders = simpleOrders;
    }

    public QueryProperty[] getQueryProperties() {
        return queryProperties;
    }

    public void setQueryProperties(QueryProperty[] queryProperties) {
        this.queryProperties = queryProperties;
    }
}
