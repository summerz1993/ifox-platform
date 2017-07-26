package com.ifox.platform.common.rest.response;

/**
 * 返回客户端数据基类
 * @author Yeager
 */
public class BaseResponse {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String desc;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BaseResponse() {
    }

    public BaseResponse(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status='" + status + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
