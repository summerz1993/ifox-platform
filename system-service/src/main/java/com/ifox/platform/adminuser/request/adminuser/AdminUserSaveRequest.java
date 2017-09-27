package com.ifox.platform.adminuser.request.adminuser;

import com.ifox.platform.adminuser.dto.base.AdminUserBaseColumns;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/15 0015.
 */
public class AdminUserSaveRequest extends AdminUserBaseColumns{

    /**
     * 密码
     */
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AdminUserSaveRequest{" +
            "checkedRole=" + Arrays.toString(checkedRole) +
            "} " + super.toString();
    }
}
