package com.ifox.platform.common.rest.request;

import com.ifox.platform.common.page.SimplePage;

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

    public SimplePage convertSimplePage() {
        return new SimplePage(this.pageSize, this.pageNo);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
            "pageSize=" + pageSize +
            ", pageNo=" + pageNo +
            '}';
    }
}
