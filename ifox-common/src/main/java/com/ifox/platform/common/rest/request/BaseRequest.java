package com.ifox.platform.common.rest.request;

/**
 * 客户端请求基类
 * @author Yeager
 */
public class BaseRequest {

    /**
     * Json Web Token
     * https://jwt.io/
     */
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
