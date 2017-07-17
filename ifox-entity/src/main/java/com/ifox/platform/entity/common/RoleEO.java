package com.ifox.platform.entity.common;

import com.ifox.platform.entity.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 * @author Yeager
 */
@Entity
@Table(name = "ifox_common_role")
public class RoleEO extends BaseEntity{

    /**
     * 角色状态
     */
    public enum RoleEOStatus {
        //有效的
        ACTIVE,
        //无效的
        INVALID
    }

    /**
     * 角色名称，中英文皆可
     */
    @Column(nullable = false)
    private String name;

    /**
     * 角色标识符，唯一，最好是方便记忆，易于理解的英文
     */
    @Column(nullable = false, unique = true)
    private String identifier;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色状态
     */
    @Column(nullable = false)
    private RoleEOStatus status;

    /**
     * 是否内置角色(不可删除)
     */
    @Column(nullable = false)
    private Boolean buildinSystem = false;

    /**
     * 对应的权限
     */
    @ManyToMany
    @JoinTable(name = "ifox_common_role_permission", joinColumns = {@JoinColumn(name = "role")}, inverseJoinColumns = {@JoinColumn(name = "permission")})
    private List<PermissionEO> permissionEOList = new ArrayList<>();


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

    public RoleEOStatus getStatus() {
        return status;
    }

    public void setStatus(RoleEOStatus status) {
        this.status = status;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    public List<PermissionEO> getPermissionEOList() {
        return permissionEOList;
    }

    public void setPermissionEOList(List<PermissionEO> permissionEOList) {
        this.permissionEOList = permissionEOList;
    }

    @Override
    public String toString() {
        return "RoleEO{" +
                "name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", buildinSystem=" + buildinSystem +
                '}';
    }
}
