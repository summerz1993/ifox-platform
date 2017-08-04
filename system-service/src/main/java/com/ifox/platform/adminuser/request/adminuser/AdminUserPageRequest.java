package com.ifox.platform.adminuser.request.adminuser;

import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.entity.sys.AdminUserEO;

import java.util.ArrayList;
import java.util.List;

public class AdminUserPageRequest extends PageRequest {

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

    /**
     * 排序条件
     */
    private List<SimpleOrder> simpleOrderList = new ArrayList<>();

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

    public List<SimpleOrder> getSimpleOrderList() {
        return simpleOrderList;
    }

    public void setSimpleOrderList(List<SimpleOrder> simpleOrderList) {
        this.simpleOrderList = simpleOrderList;
    }

    @Override
    public String toString() {
        return "AdminUserPageRequest{" +
            "loginName='" + loginName + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", simpleOrderList=" + simpleOrderList +
            "} " + super.toString();
    }
}
