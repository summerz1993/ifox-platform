package com.ifox.platform.adminuser.request;

import com.ifox.platform.entity.adminuser.AdminUserEO;

/**
 * Created by Administrator on 2017/7/15 0015.
 */
public class SaveRequest {
    private String loginName;

    private String password;

    private AdminUserEO.AdminUserEOStatus status;

    private Boolean buildinSystem = false;

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

    public AdminUserEO.AdminUserEOStatus getStatus() {
        return status;
    }

    public void setStatus(AdminUserEO.AdminUserEOStatus status) {
        this.status = status;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    @Override
    public String toString() {
        return "SaveRequest{" +
                "loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", buildinSystem=" + buildinSystem +
                '}';
    }
}
