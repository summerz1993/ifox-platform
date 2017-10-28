package com.ifox.platform.common.rest.request;

import com.ifox.platform.common.bean.SimpleOrder;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页请求
 */
public class PageRequest {

    /**
     * 当页数量
     */
    private int pageSize;

    /**
     * 页码
     */
    private int pageNo;

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

    /**
     * 转换PageRequest为spring data Pageable
     * @param pageRequest PageRequest
     * @return Pageable
     */
    public static Pageable convertToSpringDataPageable(PageRequest pageRequest) {
        return new org.springframework.data.domain.PageRequest(pageRequest.getPageNo(), pageRequest.getPageSize(), SimpleOrder.convertToSort(pageRequest.getSimpleOrderList()));
    }

    public List<SimpleOrder> getSimpleOrderList() {
        return simpleOrderList;
    }

    public void setSimpleOrderList(List<SimpleOrder> simpleOrderList) {
        this.simpleOrderList = simpleOrderList;
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
