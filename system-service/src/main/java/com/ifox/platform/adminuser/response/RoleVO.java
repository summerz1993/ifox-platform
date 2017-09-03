package com.ifox.platform.adminuser.response;

import com.ifox.platform.adminuser.dto.RoleDTO;

import java.util.List;

public class RoleVO extends RoleDTO {

    private List<String> menuPermissions;

    public List<String> getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(List<String> menuPermissions) {
        this.menuPermissions = menuPermissions;
    }

    @Override
    public String toString() {
        return "RoleVO{" +
            "menuPermissions=" + menuPermissions +
            "} " + super.toString();
    }
}
