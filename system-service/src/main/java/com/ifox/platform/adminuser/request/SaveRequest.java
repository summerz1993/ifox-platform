package com.ifox.platform.adminuser.request;

import com.ifox.platform.adminuser.dto.AdminUserBaseColumns;

/**
 * Created by Administrator on 2017/7/15 0015.
 */
public class SaveRequest extends AdminUserBaseColumns{

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
        return "SaveRequest{" +
            "password='" + password + '\'' +
            "} " + super.toString();
    }
}
