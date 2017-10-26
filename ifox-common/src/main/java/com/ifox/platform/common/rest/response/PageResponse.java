package com.ifox.platform.common.rest.response;

import java.util.List;

/**
 * 分页数据对象
 * @author Yeager
 */
public class PageResponse<T> extends BaseResponse {

    private PageResponseDetail detail;

    private List<T> data;

    public PageResponseDetail getDetail() {
        return detail;
    }

    public void setDetail(PageResponseDetail detail) {
        this.detail = detail;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PageResponse() {
    }

    public PageResponse(Integer status, String desc, PageResponseDetail detail, List<T> data) {
        super(status, desc);
        this.detail = detail;
        this.data = data;
    }
}
