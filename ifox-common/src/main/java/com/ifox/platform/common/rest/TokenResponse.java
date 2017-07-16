package com.ifox.platform.common.rest;

/**
 * 登陆成功后，返回token
 * @author Yeager
 */
public class TokenResponse extends BaseResponse{

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }


}
