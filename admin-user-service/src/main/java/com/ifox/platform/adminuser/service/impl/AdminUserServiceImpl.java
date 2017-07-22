package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.dao.adminuser.AdminUserDao;
import com.ifox.platform.entity.adminuser.AdminUserEO;
import com.ifox.platform.entity.common.RoleEO;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import com.ifox.platform.utility.jwt.JWTPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminUserServiceImpl extends GenericServiceImpl<AdminUserEO, String> implements AdminUserService{

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

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
        AdminUserEO adminUserEO = getAdminUserEOByLoginName(loginName);
        boolean validatePassword;
        try {
            validatePassword = PasswordUtil.validatePassword(password, adminUserEO.getSalt(), adminUserEO.getPassword());
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            validatePassword = false;
        }

        return validatePassword;
    }

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return 用户信息DTO
     */
    @Override
    public AdminUserDTO getByLoginName(String loginName) {
        AdminUserDTO adminUserDTO = null;
        try {
            AdminUserEO adminUserEO = getAdminUserEOByLoginName(loginName);
            adminUserDTO = new AdminUserDTO();
            BeanUtils.copyProperties(adminUserEO, adminUserDTO);
        } catch (NotFoundAdminUserException | RepeatedAdminUserException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        return adminUserDTO;
    }

    /**
     * 根据登录名生成PayLoad
     * @param loginName 登录名
     * @return JWTPayload
     */
    @Override
    public JWTPayload generatePayload(String loginName) {
        JWTPayload payload = new JWTPayload();
        AdminUserEO adminUserEO = null;
        try {
            adminUserEO = getAdminUserEOByLoginName(loginName);
        } catch (NotFoundAdminUserException | RepeatedAdminUserException e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
        }
        if (adminUserEO != null) {
            payload.setIss(env.getProperty("jwt.payload.iss"));
            payload.setIat(new Date());
            payload.setExp(DateTimeUtil.plusMinBaseOnCurrentDate(Integer.valueOf(env.getProperty("jwt.payload.exp"))));  //过期时间
            payload.setJti(UUIDUtil.randomUUID());
            payload.setUserId(adminUserEO.getId());
            payload.setLoginName(adminUserEO.getLoginName());

            List<RoleEO> roleEOList = adminUserEO.getRoleEOList();
            List<String> roleIdList = new ArrayList<>();
            for (RoleEO role :
                roleEOList) {
                roleIdList.add(role.getId());
            }
            int size = roleIdList.size();
            payload.setRoleIdList(roleIdList.toArray(new String[size]));

        }
        return payload;
    }

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return 用户信息EO
     */
    private AdminUserEO getAdminUserEOByLoginName(String loginName) throws NotFoundAdminUserException, RepeatedAdminUserException {
        QueryProperty queryProperty = new QueryProperty("loginName", EnumDao.Operation.EQUAL, loginName);
        List<AdminUserEO> adminUserEOList = listByQueryProperty(new QueryProperty[]{queryProperty});
        if (CollectionUtils.isEmpty(adminUserEOList)) {
            throw new NotFoundAdminUserException("为找到此用户, loginName:" + loginName);
        }
        if (adminUserEOList.size() > 1) {
            throw new RepeatedAdminUserException("数据库存在重复用户名, loginName:" + loginName);
        }
        return adminUserEOList.get(0);
    }

}
