package com.ifox.platform.system.entity;

import com.ifox.platform.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 * @author Yeager
 */
@Entity
@Table(name = "ifox_sys_role")
@EntityListeners(AuditingEntityListener.class)
public class RoleEO extends BaseEntity {

    /**
     * 角色状态
     */
    public enum RoleEOStatus {
        //无效的
        INVALID,
        //有效的
        ACTIVE
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
    @Column(length = 500)
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
     * 对应的菜单权限
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ifox_sys_role_menu_permission", joinColumns = {@JoinColumn(name = "role")}, inverseJoinColumns = {@JoinColumn(name = "menu_permission")})
    private List<MenuPermissionEO> menuPermissionEOList = new ArrayList<>();


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

    public List<MenuPermissionEO> getMenuPermissionEOList() {
        return menuPermissionEOList;
    }

    public void setMenuPermissionEOList(List<MenuPermissionEO> menuPermissionEOList) {
        this.menuPermissionEOList = menuPermissionEOList;
    }

    @Override
    public String toString() {
        return "RoleEO{" +
            "name='" + name + '\'' +
            ", identifier='" + identifier + '\'' +
            ", remark='" + remark + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            "} " + super.toString();
    }
}
