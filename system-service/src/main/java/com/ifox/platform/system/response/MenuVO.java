package com.ifox.platform.system.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class MenuVO {
    /**
     * menu类型：menu
     */
    public static final String MENU_PERMISSION_TYPE_MENU = "menu";

    /**
     * menu类型：button
     */
    public static final String MENU_PERMISSION_TYPE_BUTTON = "button";

    /**
     * 菜单权限id
     */
    private String id;

    /**
     * 菜单权限名称
     */
    private String text;

    /**
     * 子菜单
     */
    private List<MenuVO> children;

    /**
     * 菜单类型（左侧菜单、按钮）
     * menu or button
     */
    private String type;

    /**
     * 菜单级别
     */
    private Integer level;

    /**
     * 父菜单ID
     */
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @JsonIgnore
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MenuVO{" +
            "id='" + id + '\'' +
            ", text='" + text + '\'' +
            ", children=" + children +
            ", type='" + type + '\'' +
            ", level=" + level +
            ", parentId='" + parentId + '\'' +
            '}';
    }
}
