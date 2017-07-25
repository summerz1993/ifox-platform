package com.ifox.platform.adminuser.request;

/**
 * 登录请求
 * @author Yeager
 */
public class AdminUserLoginRequest {

    private String loginName;

    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminUserLoginRequest{" +
            "loginName='" + loginName + '\'' +
            '}';
    }
}
