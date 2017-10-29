package com.ifox.platform.system.request.adminuser;

import com.ifox.platform.system.entity.AdminUserEO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Arrays;

/**
 * Created by Yeager on 2017/7/15 0015.
 */
public class AdminUserSaveRequest {

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 选中的角色
     */
    private String[] checkedRole;

    /**
     * 登陆名
     */
    @NotBlank
    @Size(min = 1, max = 30)
    private String loginName;

    /**
     * 状态
     */
    @NotBlank
    private AdminUserEO.AdminUserEOStatus status;

    /**
     * 是否内置
     */
    @NotBlank
    private Boolean buildinSystem = false;

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
    private String mobile;

    /**
     * 备注
     */
    private String remark;

    /**
     * 头像
     */
    private String headPortrait;

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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public AdminUserEO.AdminUserEOStatus getStatus() {
        return status;
    }

    public void setStatus(AdminUserEO.AdminUserEOStatus status) {
        this.status = status;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    @Override
    public String toString() {
        return "AdminUserSaveRequest{" +
            "password='" + password + '\'' +
            ", checkedRole=" + Arrays.toString(checkedRole) +
            ", loginName='" + loginName + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", nickName='" + nickName + '\'' +
            ", email='" + email + '\'' +
            ", mobile='" + mobile + '\'' +
            ", remark='" + remark + '\'' +
            ", headPortrait='" + headPortrait + '\'' +
            '}';
    }
}
