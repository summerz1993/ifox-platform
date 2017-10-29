package com.ifox.platform.system.dto;

import com.ifox.platform.system.entity.MenuPermissionEO;
import com.ifox.platform.system.response.MenuVO;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class MenuPermissionDTO {

    /**
     * ID
     */
    @NotBlank
    private String id;

    /**
     * 名称
     */
    @NotBlank
    private String name;

    /**
     * 链接, 例子: /adminUser/save
     */
    @NotBlank
    private String url;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单级别, 从1开始排列
     */
    private Integer level;

    /**
     * 是否为按钮(不在左侧菜单中显示)
     */
    @NotBlank
    private Boolean button;

    /**
     * 父菜单
     */
    @NotBlank
    private String parentId;

    /**
     * 状态
     */
    @NotBlank
    private MenuPermissionEO.MenuEOStatus status;

    /**
     * 是否内置菜单(不可删除)
     */
    @NotBlank
    private Boolean buildinSystem = false;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 所属资源
     */
    @NotBlank
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

    public MenuPermissionEO.MenuEOStatus getStatus() {
        return status;
    }

    public void setStatus(MenuPermissionEO.MenuEOStatus status) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<MenuVO> convertEOToVO(List<MenuPermissionEO> menuPermissionEOList) {
        List<MenuVO> menuVOS = new ArrayList<>();
        for (MenuPermissionEO menuPermissionDTO : menuPermissionEOList){
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menuPermissionDTO.getId());
            menuVO.setText(menuPermissionDTO.getName());
            menuVO.setType(menuPermissionDTO.getButton() ? MenuVO.MENU_PERMISSION_TYPE_BUTTON : MenuVO.MENU_PERMISSION_TYPE_MENU);
            menuVO.setLevel(menuPermissionDTO.getLevel());
            menuVO.setParentId(menuPermissionDTO.getParentId());
            menuVOS.add(menuVO);
        }
        return menuVOS;
    }

    @Override
    public String toString() {
        return "MenuPermissionDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", url='" + url + '\'' +
            ", remark='" + remark + '\'' +
            ", level=" + level +
            ", button=" + button +
            ", parentId='" + parentId + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", creator='" + creator + '\'' +
            ", resource='" + resource + '\'' +
            '}';
    }
}
