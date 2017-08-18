package com.ifox.platform.adminuser.request.role;

import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.entity.sys.RoleEO;

public class RolePageRequest extends PageRequest {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色状态
     */
    private RoleEO.RoleEOStatus status;

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

    @Override
    public String toString() {
        return "RolePageRequest{" +
            "name='" + name + '\'' +
            ", status=" + status +
            "} " + super.toString();
    }
}
