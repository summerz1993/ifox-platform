package com.ifox.platform.system.request.adminuser;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 登录请求
 * @author Yeager
 */
public class AdminUserLoginRequest {

    @NotBlank
    private String loginName;

    @NotBlank
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
