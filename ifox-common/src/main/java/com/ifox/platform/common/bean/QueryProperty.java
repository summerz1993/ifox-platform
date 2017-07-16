package com.ifox.platform.common.bean;


import com.ifox.platform.common.enums.EnumDao;

/**
 * @author Yeager
 *
 * 属性查询
 */
public class QueryProperty {

    /**
     * 属性
     */
    private String property;

    /**
     * 操作符
     */
    private EnumDao.Operation operation;

    /**
     * 值
     */
    private String value;

    public QueryProperty(String property, EnumDao.Operation operation, String value) {
        this.property = property;
        this.operation = operation;
        this.value = value;
    }

    public QueryProperty() {}

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EnumDao.Operation getOperation() {
        return operation;
    }

    public void setOperation(EnumDao.Operation operation) {
        this.operation = operation;
    }
}
