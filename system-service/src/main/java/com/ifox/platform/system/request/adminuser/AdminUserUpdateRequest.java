package com.ifox.platform.system.request.adminuser;

import com.ifox.platform.system.dto.AdminUserDTO;

import java.util.Arrays;

public class AdminUserUpdateRequest extends AdminUserDTO {

    /**
     * 选中的角色
     */
    private String[] checkedRole;

    public String[] getCheckedRole() {
        return checkedRole;
    }

    public void setCheckedRole(String[] checkedRole) {
        this.checkedRole = checkedRole;
    }

    @Override
    public String toString() {
        return "AdminUserUpdateRequest{" +
            "checkedRole=" + Arrays.toString(checkedRole) +
            "} " + super.toString();
    }
}
