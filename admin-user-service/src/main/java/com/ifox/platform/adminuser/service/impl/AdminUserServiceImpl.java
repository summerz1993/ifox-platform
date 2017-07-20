package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.dao.adminuser.AdminUserDao;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by yezhang on 7/14/2017.
 */
@Service
public class AdminUserServiceImpl extends GenericServiceImpl<AdminUserEO, String> implements AdminUserService{

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    public void setGenericDao(AdminUserDao adminUserDao){
        super.genericDao = adminUserDao;
    }

    /**
     * 验证用户名和密码是否正确
     * @param loginName 用户名
     * @param password 密码
     * @return true:存在 false:不存在
     */
    @Override
    public Boolean validLoginNameAndPassword(String loginName, String password) throws NotFoundAdminUserException, RepeatedAdminUserException {
        QueryProperty queryProperty = new QueryProperty("loginName", EnumDao.Operation.EQUAL, loginName);
        List<AdminUserEO> adminUserEOList = listByQueryProperty(new QueryProperty[]{queryProperty});
        if (CollectionUtils.isEmpty(adminUserEOList)) {
            throw new NotFoundAdminUserException("为找到此用户, loginName:" + loginName);
        }
        if (adminUserEOList.size() > 1) {
            throw new RepeatedAdminUserException("重复用户名, loginName:" + loginName);
        }
        AdminUserEO adminUserEO = adminUserEOList.get(0);
        boolean validatePassword;
        try {
            validatePassword = PasswordUtil.validatePassword(password, adminUserEO.getPassword());
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            validatePassword = false;
        }

        return validatePassword;
    }

}
