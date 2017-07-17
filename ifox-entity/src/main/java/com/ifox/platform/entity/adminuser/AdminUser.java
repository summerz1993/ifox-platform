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

    /**
     * 登录名
     */
    @Column(nullable = false, length = 30, unique = true)
    private String loginName;

    /**
     * 密码
     */
    @Column(nullable = false)
    private String password;

    /**
     * 状态
     */
    @Column(nullable = false)
    private AdminUserStatus status;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @Column(length = 20)
    private String mobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户状态
     */
    public enum AdminUserStatus{
        //有效的
        ACTIVE,
        //无效的
        INVALID
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public AdminUserStatus getStatus() {
        return status;
    }

    public void setStatus(AdminUserStatus status) {
        this.status = status;
    }
}
