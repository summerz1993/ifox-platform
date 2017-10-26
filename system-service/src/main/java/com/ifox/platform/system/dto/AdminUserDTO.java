package com.ifox.platform.system.dto;

import com.ifox.platform.system.dto.base.AdminUserBaseColumns;

public class AdminUserDTO extends AdminUserBaseColumns {

    /**
     * ID
     */
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "AdminUserDTO{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
