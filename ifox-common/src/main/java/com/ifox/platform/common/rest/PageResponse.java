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

    /**
     * 分页内容
     */
    class PageInfo{

        /**
         * 总记录数
         */
        private int totalCount;

        /**
         * 当页数量
         */
        private int pageSize;

        /**
         * 页码
         */
        private int pageNo;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

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
    }

}
