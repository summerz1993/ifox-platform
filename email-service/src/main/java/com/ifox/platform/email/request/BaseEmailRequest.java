package com.ifox.platform.email.request;

public class BaseEmailRequest {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseEmailRequest{" +
            "token='" + token + '\'' +
            '}';
    }
}
