package com.ifox.platform.adminuser.request.adminuser;

import com.ifox.platform.adminuser.dto.base.AdminUserBaseColumns;

public class AdminUserUpdateRequest extends AdminUserBaseColumns {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AdminUserUpdateRequest{" +
            "id='" + id + '\'' +
            "} " + super.toString();
    }
}
