package com.ifox.platform.system.request.menuPermission;

import com.ifox.platform.system.dto.base.MenuPermissionBaseColumns;

public class MenuPermissionRequest extends MenuPermissionBaseColumns {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MenuPermissionRequest{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
