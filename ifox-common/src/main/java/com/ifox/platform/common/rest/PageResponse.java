package com.ifox.platform.common.rest;

import java.util.List;

/**
 * 分页数据对象
 * @author Yeager
 */
public class PageResponse<T> extends BaseResponse{

    private PageInfo pageInfo;

    private List<T> data;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
