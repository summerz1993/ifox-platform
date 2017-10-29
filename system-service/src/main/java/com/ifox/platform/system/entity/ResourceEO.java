package com.ifox.platform.system.entity;


import com.ifox.platform.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 资源数据entity
 * @author Yeager
 */
@Entity
@Table(name = "ifox_common_resource")
@EntityListeners(AuditingEntityListener.class)
public class ResourceEO extends BaseEntity {

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
     * 资源名称
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 资源对应的控制器的RequestMapping, 比如:adminUser
     */
    @Column(nullable = false, unique = true)
    private String controller;

    /**
     * 资源类型
     */
    @Column(nullable = false)
    private ResourceEOType type;

    /**
     * 描述
     */
    private String remark;

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

    public ResourceEOType getType() {
        return type;
    }

    public void setType(ResourceEOType type) {
        this.type = type;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return "ResourceEO{" +
            "name='" + name + '\'' +
            ", controller='" + controller + '\'' +
            ", type=" + type +
            ", remark='" + remark + '\'' +
            "} " + super.toString();
    }
}
