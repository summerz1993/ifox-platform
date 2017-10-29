package com.ifox.platform.system.dto;

import com.ifox.platform.system.entity.RoleEO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {

    /**
     * ID
     */
    @NotBlank
    private String id;

    /**
     * 角色名称
     */
    @NotBlank
    private String name;

    /**
     * 角色标识符
     */
    @NotBlank
    private String identifier;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色状态
     */
    @NotBlank
    private RoleEO.RoleEOStatus status;

    /**
     * 是否内置角色
     */
    @NotBlank
    private Boolean buildinSystem = false;

    /**
     * 菜单权限id
     */
    private List<String> menuPermissions = new ArrayList<>();

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

    public List<String> getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(List<String> menuPermissions) {
        this.menuPermissions = menuPermissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", identifier='" + identifier + '\'' +
            ", remark='" + remark + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", menuPermissions=" + menuPermissions +
            "} ";
    }
}
