package com.ifox.platform.entity.adminuser;

import com.ifox.platform.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Yeager
 *
 * 后台用户信息类
 */
@Entity
@Table(name = "ifox_admin_user")
public class AdminUser extends BaseEntity {

    @Column(nullable = false, length = 30, unique = true)
    private String loginName;

    @Column(nullable = false)
    private String password;

    private String email;

    private String mobile;

    private String remark;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
