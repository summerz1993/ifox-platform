package com.ifox.platform.adminuser.request.role;

import com.ifox.platform.adminuser.dto.base.RoleBaseColumns;
import com.ifox.platform.entity.sys.MenuPermissionEO;

import java.util.ArrayList;
import java.util.List;

public class RoleUpdateRequest extends RoleBaseColumns {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleUpdateRequest{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
