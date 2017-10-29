package com.ifox.platform.system.entity;

import com.ifox.platform.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 菜单信息表
 * @author Yeager
 */
@Entity
@Table(name = "ifox_sys_menu_permission")
@EntityListeners(AuditingEntityListener.class)
public class MenuPermissionEO extends BaseEntity {

    /**
     * 菜单状态
     */
    public enum MenuEOStatus {
        //无效的
        INVALID,
        //有效的
        ACTIVE

    }

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 链接, 例子: /adminUser/save
     */
    @Column(nullable = false)
    private String url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单级别, 从1开始排列
     */
    @Column(nullable = false)
    private Integer level;

    /**
     * 是否为按钮(不在左侧菜单中显示)
     */
    @Column(nullable = false)
    private Boolean button;

    /**
     * 父菜单
     */
    @Column
    private String parentId;

    /**
     * 状态
     */
    @Column(nullable = false)
    private MenuEOStatus status;

    /**
     * 是否内置菜单(不可删除)
     */
    @Column(nullable = false)
    private Boolean buildinSystem = false;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 所属资源
     */
    @Column
    private String resource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getButton() {
        return button;
    }

    public void setButton(Boolean button) {
        this.button = button;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public MenuEOStatus getStatus() {
        return status;
    }

    public void setStatus(MenuEOStatus status) {
        this.status = status;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "MenuPermissionEO{" +
            "name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", remark='" + remark + '\'' +
            ", level=" + level +
            ", button=" + button +
            ", parentId='" + parentId + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", creator='" + creator + '\'' +
            ", resource='" + resource + '\'' +
            "} " + super.toString();
    }
}
