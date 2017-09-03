package com.ifox.platform.adminuser.request.role;

import com.ifox.platform.adminuser.dto.base.RoleBaseColumns;

import java.util.List;

public class RoleSaveRequest extends RoleBaseColumns {

    private List<String> menuPermissions;

    public List<String> getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(List<String> menuPermissions) {
        this.menuPermissions = menuPermissions;
    }

    @Override
    public String toString() {
        return "RoleSaveRequest{" +
            "menuPermissions=" + menuPermissions +
            "} " + super.toString();
    }
}
