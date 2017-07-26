package com.ifox.platform.common.rest.response;

/**
 * 单条数据返回
 * @param <T>
 */
public class OneResponse<T> extends BaseResponse {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public OneResponse() {
    }

    public OneResponse(Integer status, String desc, T data) {
        super(status, desc);
        this.data = data;
    }
}
