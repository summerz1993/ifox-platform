package com.ifox.platform.common.rest.response;

import com.ifox.platform.common.rest.PageInfo;

import java.util.List;

/**
 * 分页数据对象
 * @author Yeager
 */
public class PageResponse<T> extends BaseResponse {

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

    public PageResponse() {
    }

    public PageResponse(Integer status, String desc, PageInfo pageInfo, List<T> data) {
        super(status, desc);
        this.pageInfo = pageInfo;
        this.data = data;
    }
}
