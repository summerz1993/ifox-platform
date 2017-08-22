package com.ifox.platform.adminuser.request.adminuser;

import com.ifox.platform.adminuser.dto.base.AdminUserBaseColumns;

/**
 * Created by Administrator on 2017/7/15 0015.
 */
public class AdminUserSaveRequest extends AdminUserBaseColumns{

    /**
     * 密码
     */
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "AdminUserSaveRequest{" +
            "password='" + password + '\'' +
            "} " + super.toString();
    }
}
