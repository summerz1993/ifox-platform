package com.ifox.platform.entity.common;

import com.ifox.platform.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源数据entity
 * @author Yeager
 */
@Entity
@Table(name = "ifox_common_resource")
public class ResourceEO extends BaseEntity{

    /**
     * 资源类型
     */
    public enum ResourceEOType{
        //公共资源
        PUBLIC,
        //角色资源
        ROLE,
        //私人资源
        PERSONAL
    }

    /**
     * 资源名称，对应Controller RequestMapping
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 资源类型
     */
    @Column(nullable = false)
    private ResourceEOType type;

    /**
     * 描述
     */
    private String remark;

    /**
     * 所有权限信息
     */
    @OneToMany(mappedBy = "resourceEO")
    private List<PermissionEO> permissionEOList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PermissionEO> getPermissionEOList() {
        return permissionEOList;
    }

    public void setPermissionEOList(List<PermissionEO> permissionEOList) {
        this.permissionEOList = permissionEOList;
    }

    public ResourceEOType getType() {
        return type;
    }

    public void setType(ResourceEOType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ResourceEO{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                '}';
    }
}
