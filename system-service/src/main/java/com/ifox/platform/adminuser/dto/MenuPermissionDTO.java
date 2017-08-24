package com.ifox.platform.adminuser.dto;

import com.ifox.platform.adminuser.dto.base.MenuPermissionBaseColumns;
import com.ifox.platform.adminuser.response.MenuVO;

import java.util.ArrayList;
import java.util.List;

public class MenuPermissionDTO extends MenuPermissionBaseColumns {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<MenuVO> convertToVO(List<MenuPermissionDTO> menuPermissionDTOList) {
        List<MenuVO> menuVOS = new ArrayList<>();
        for (MenuPermissionDTO menuPermissionDTO : menuPermissionDTOList){
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
            "} " + super.toString();
    }
}
