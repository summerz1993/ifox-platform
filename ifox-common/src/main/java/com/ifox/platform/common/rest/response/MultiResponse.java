package com.ifox.platform.common.rest.response;

import com.ifox.platform.common.rest.response.BaseResponse;

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

}
