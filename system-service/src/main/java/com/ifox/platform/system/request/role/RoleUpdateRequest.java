package com.ifox.platform.system.request.role;

import com.ifox.platform.system.dto.base.RoleBaseColumns;

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
