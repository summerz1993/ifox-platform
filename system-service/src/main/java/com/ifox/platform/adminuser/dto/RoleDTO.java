package com.ifox.platform.adminuser.dto;

import com.ifox.platform.adminuser.dto.base.RoleBaseColumns;

public class RoleDTO extends RoleBaseColumns {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
