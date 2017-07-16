package com.ifox.platform.common.bean;


import com.ifox.platform.common.enums.EnumDao;

/**
 * @author Yeager
 *
 * 排序基类
 */
public class SimpleOrder {

    /**
     * 排序属性
     */
    private String property;

    /**
     * 排序方式
     */
    private EnumDao.OrderMode orderMode = EnumDao.OrderMode.ASC;

    public SimpleOrder() {
    }

    public SimpleOrder(String property, EnumDao.OrderMode orderMode) {
        this.property = property;
        this.orderMode = orderMode;
    }

    public SimpleOrder(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public EnumDao.OrderMode getOrderMode() {
        return orderMode;
    }

    public void setOrderMode(EnumDao.OrderMode orderMode) {
        this.orderMode = orderMode;
    }
}
