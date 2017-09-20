package com.ifox.platform.common.rest.response;

import java.util.List;

/**
 * 多条数据返回
 * @author Yeager
 */
public class MultiResponse<T> extends BaseResponse {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public MultiResponse() {
    }

    public MultiResponse(Integer status, String desc, List<T> data) {
        super(status, desc);
        this.data = data;
    }
}
