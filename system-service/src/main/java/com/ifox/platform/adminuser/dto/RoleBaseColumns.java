package com.ifox.platform.adminuser.dto;

import com.ifox.platform.entity.sys.RoleEO;

public class RoleBaseColumns {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识符
     */
    private String identifier;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色状态
     */
    private RoleEO.RoleEOStatus status;

    /**
     * 是否内置角色
     */
    private Boolean buildinSystem = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RoleEO.RoleEOStatus getStatus() {
        return status;
    }

    public void setStatus(RoleEO.RoleEOStatus status) {
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
        return "RoleBaseColumns{" +
            "name='" + name + '\'' +
            ", identifier='" + identifier + '\'' +
            ", remark='" + remark + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            '}';
    }
}
