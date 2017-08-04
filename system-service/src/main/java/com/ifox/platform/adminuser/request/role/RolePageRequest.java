package com.ifox.platform.adminuser.request.role;

import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.entity.sys.RoleEO;

import java.util.ArrayList;
import java.util.List;

public class RolePageRequest extends PageRequest {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色状态
     */
    private RoleEO.RoleEOStatus status;

    /**
     * 排序条件
     */
    private List<SimpleOrder> simpleOrderList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleEO.RoleEOStatus getStatus() {
        return status;
    }

    public void setStatus(RoleEO.RoleEOStatus status) {
        this.status = status;
    }

    public List<SimpleOrder> getSimpleOrderList() {
        return simpleOrderList;
    }

    public void setSimpleOrderList(List<SimpleOrder> simpleOrderList) {
        this.simpleOrderList = simpleOrderList;
    }

    @Override
    public String toString() {
        return "RolePageRequest{" +
            "name='" + name + '\'' +
            ", status=" + status +
            ", simpleOrderList=" + simpleOrderList +
            "} " + super.toString();
    }
}
