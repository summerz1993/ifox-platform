package com.ifox.platform.entity.adminuser;

import com.ifox.platform.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yezhang on 6/7/2017.
 */
@Entity
@Table(name = "ifox_admin_role")
public class AdminRole extends BaseEntity {

    @Column
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
