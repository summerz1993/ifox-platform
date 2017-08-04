package com.ifox.platform.common.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yeager
 *
 * 查询条件集合
 */
public class QueryConditions {

    /**
     * 查询的字段,可以为Null
     */
    private String[] properties;

    /**
     * 查询条件
     */
    private List<QueryProperty> queryPropertyList = new ArrayList<>();

    /**
     * 排序条件
     */
    private List<SimpleOrder> simpleOrderList = new ArrayList<>();

    public QueryConditions() {
    }

    public QueryConditions(String[] properties, List<QueryProperty> queryPropertyList, List<SimpleOrder> simpleOrderList) {
        this.properties = properties;
        this.queryPropertyList = queryPropertyList;
        this.simpleOrderList = simpleOrderList;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public List<QueryProperty> getQueryPropertyList() {
        return queryPropertyList;
    }

    public void setQueryPropertyList(List<QueryProperty> queryPropertyList) {
        this.queryPropertyList = queryPropertyList;
    }

    public List<SimpleOrder> getSimpleOrderList() {
        return simpleOrderList;
    }

    public void setSimpleOrderList(List<SimpleOrder> simpleOrderList) {
        this.simpleOrderList = simpleOrderList;
    }
}
