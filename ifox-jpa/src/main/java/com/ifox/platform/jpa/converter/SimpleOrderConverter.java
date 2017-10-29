package com.ifox.platform.jpa.converter;

import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.enums.EnumDao;
import org.springframework.data.domain.Sort;

import java.util.List;

public class SimpleOrderConverter {

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

}
