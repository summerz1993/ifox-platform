package com.ifox.platform.common.rest.response;

/**
 * 登陆成功后，返回token
 * @author Yeager
 */
public class TokenResponse extends BaseResponse {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
