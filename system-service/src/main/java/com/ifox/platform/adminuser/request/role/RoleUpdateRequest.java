package com.ifox.platform.adminuser.request.role;

import com.ifox.platform.adminuser.dto.base.RoleBaseColumns;

import java.util.List;

public class RoleUpdateRequest extends RoleBaseColumns {
    private String id;

    private List<String> menuPermissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getMenuPermissions() {
        return menuPermissions;
    }

    public void setMenuPermissions(List<String> menuPermissions) {
        this.menuPermissions = menuPermissions;
    }

    @Override
    public String toString() {
        return "RoleUpdateRequest{" +
            "id='" + id + '\'' +
            ", menuPermissions=" + menuPermissions +
            "} " + super.toString();
    }
}
