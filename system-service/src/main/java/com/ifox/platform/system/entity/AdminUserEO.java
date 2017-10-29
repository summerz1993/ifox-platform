package com.ifox.platform.system.entity;

import com.ifox.platform.jpa.entity.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yeager
 *
 * 后台用户信息类
 */
@Entity
@Table(name = "ifox_sys_admin_user")
@EntityListeners(AuditingEntityListener.class)
public class AdminUserEO extends BaseEntity {

    /**
     * 用户状态
     */
    public enum AdminUserEOStatus {
        //无效的
        INVALID,
        //有效的
        ACTIVE
    }

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
     * 盐
     */
    @Column(nullable = false)
    private String salt;

    /**
     * 状态
     */
    @Column(nullable = false)
    private AdminUserEOStatus status;

    /**
     * 是否内置用户(不可删除)
     */
    @Column(nullable = false)
    private Boolean buildinSystem = false;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
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
    @Column(length = 500)
    private String remark;

    /**
     * 头像
     */
    @Column
    private String headPortrait;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 所属角色
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ifox_sys_admin_user_role", joinColumns = {@JoinColumn(name = "admin_user")}, inverseJoinColumns = {@JoinColumn(name = "role")})
    private List<RoleEO> roleEOList = new ArrayList<>();


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

    public AdminUserEOStatus getStatus() {
        return status;
    }

    public void setStatus(AdminUserEOStatus status) {
        this.status = status;
    }

    public Boolean getBuildinSystem() {
        return buildinSystem;
    }

    public void setBuildinSystem(Boolean buildinSystem) {
        this.buildinSystem = buildinSystem;
    }

    public List<RoleEO> getRoleEOList() {
        return roleEOList;
    }

    public void setRoleEOList(List<RoleEO> roleEOList) {
        this.roleEOList = roleEOList;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    @Override
    public String toString() {
        return "AdminUserEO{" +
            "loginName='" + loginName + '\'' +
            ", password='" + password + '\'' +
            ", salt='" + salt + '\'' +
            ", status=" + status +
            ", buildinSystem=" + buildinSystem +
            ", nickName='" + nickName + '\'' +
            ", email='" + email + '\'' +
            ", mobile='" + mobile + '\'' +
            ", remark='" + remark + '\'' +
            ", headPortrait='" + headPortrait + '\'' +
            ", creator='" + creator + '\'' +
            "} " + super.toString();
    }
}
