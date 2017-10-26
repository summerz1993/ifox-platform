package com.ifox.platform.system.request.adminuser;


import com.ifox.platform.system.entity.AdminUserEO;

public class AdminUserQueryRequest {

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 状态
     */
    private AdminUserEO.AdminUserEOStatus status;

    /**
     * 是否内置
     */
    private Boolean buildinSystem;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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
        return "AdminUserQueryRequest{" +
            "loginName='" + loginName + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            '}';
    }
}
