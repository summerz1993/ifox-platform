package com.ifox.platform.entity.common;

import com.ifox.platform.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * 权限entity
 * @author Yeager
 */
@Entity
@Table(name = "ifox_common_permission")
public class PermissionEO extends BaseEntity{

    /**
     * 权限状态
     */
    public enum PermissionEOStatus {
        //有效的
        ACTIVE,
        //无效的
        INVALID
    }

    /**
     * 对应资源的操作
     */
    public enum PermissionEOAction {
        //增加
        CREATE,
        //删除
        DELETE,
        //修改
        UPDATE,
        //查看
        RETRIEVE
    }

    /**
     * 权限名称
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 状态
     */
    @Column(nullable = false)
    private PermissionEOStatus status;

    /**
     * 操作
     */
    @Column(nullable = false)
    private PermissionEOAction action;

    /**
     * 是否内置权限(不可删除)
     */
    @Column(nullable = false)
    private Boolean buildinSystem = false;

    /**
     * 所属资源
     */
    @ManyToOne
    @JoinColumn(name = "resource", nullable = false)
    private ResourceEO resourceEO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissionEOStatus getStatus() {
        return status;
    }

    public void setStatus(PermissionEOStatus status) {
        this.status = status;
    }

    public PermissionEOAction getAction() {
        return action;
    }

    public void setAction(PermissionEOAction action) {
        this.action = action;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    public ResourceEO getResourceEO() {
        return resourceEO;
    }

    public void setResourceEO(ResourceEO resourceEO) {
        this.resourceEO = resourceEO;
    }

    @Override
    public String toString() {
        return "PermissionEO{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", action=" + action +
                ", buildinSystem=" + buildinSystem +
                ", resourceEO=" + resourceEO +
                '}';
    }
}
