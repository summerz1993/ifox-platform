package com.ifox.platform.common.bean;


import com.ifox.platform.common.enums.EnumDao;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    /**
     * 转换自定义的SimpleOrder为spring data的Sort
     * @param simpleOrderList 自定义的SimpleOrder
     * @return Sort
     */
    public static Sort convertToSort(List<SimpleOrder> simpleOrderList) {
        Sort sort = null;
        for (SimpleOrder simpleOrder : simpleOrderList) {
            if (sort == null) {
                sort = new Sort(simpleOrder.getOrderMode() == EnumDao.OrderMode.DESC ? Sort.Direction.DESC : Sort.Direction.ASC, simpleOrder.getProperty());
            } else {
                sort.and(new Sort(simpleOrder.getOrderMode() == EnumDao.OrderMode.DESC ? Sort.Direction.DESC : Sort.Direction.ASC, simpleOrder.getProperty()));
            }
        }
        return sort;
    }

    @Override
    public String toString() {
        return "SimpleOrder{" +
            "property='" + property + '\'' +
            ", orderMode=" + orderMode +
            '}';
    }
}
