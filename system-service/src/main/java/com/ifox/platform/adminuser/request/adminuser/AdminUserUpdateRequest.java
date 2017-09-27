package com.ifox.platform.adminuser.request.adminuser;

import com.ifox.platform.adminuser.dto.base.AdminUserBaseColumns;

import java.util.Arrays;

public class AdminUserUpdateRequest extends AdminUserBaseColumns {

    private String id;

    /**
     * 选中的角色
     */
    private String[] checkedRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getCheckedRole() {
        return checkedRole;
    }

    public void setCheckedRole(String[] checkedRole) {
        this.checkedRole = checkedRole;
    }

    @Override
    public String toString() {
        return "AdminUserUpdateRequest{" +
            "id='" + id + '\'' +
            ", checkedRole=" + Arrays.toString(checkedRole) +
            "} " + super.toString();
    }
}
