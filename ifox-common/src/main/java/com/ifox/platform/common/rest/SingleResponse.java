package com.ifox.platform.common.rest;

/**
 * Created by delin.teng on 7/24/2017.
 */
public class SingleResponse<T> extends BaseResponse{
    /**
     * response detail
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
