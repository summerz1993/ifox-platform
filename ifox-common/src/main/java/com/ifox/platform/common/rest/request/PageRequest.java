package com.ifox.platform.common.rest.request;

import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.enums.EnumDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页请求
 */
public class PageRequest {

    /**
     * 当页数量
     */
    private int pageSize = 10;

    /**
     * 页码
     */
    private int pageNo = 1;

    /**
     * 排序条件
     */
    private List<SimpleOrder> simpleOrderList = new ArrayList<>();

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<SimpleOrder> getSimpleOrderList() {
        return simpleOrderList;
    }

    public void setSimpleOrderList(List<SimpleOrder> simpleOrderList) {
        this.simpleOrderList = simpleOrderList;
    }

    public PageRequest() {
        if (simpleOrderList.size() == 0) {
            //添加默认的排序: create_date倒叙
            simpleOrderList.add(new SimpleOrder("createDate", EnumDao.OrderMode.DESC));
        }
    }

    @Override
    public String toString() {
        return "PageRequest{" +
            "pageSize=" + pageSize +
            ", pageNo=" + pageNo +
            ", simpleOrderList=" + simpleOrderList +
            '}';
    }
}
