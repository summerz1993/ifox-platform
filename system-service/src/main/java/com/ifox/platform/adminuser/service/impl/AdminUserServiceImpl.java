package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.modelmapper.AdminUserEOMapDTO;
import com.ifox.platform.adminuser.request.adminuser.AdminUserPageRequest;
import com.ifox.platform.adminuser.request.adminuser.AdminUserQueryRequest;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.dao.sys.AdminUserDao;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.entity.sys.RoleEO;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import com.ifox.platform.utility.jwt.JWTPayload;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.BUILDIN_SYSTEM_EXP;
import static com.ifox.platform.common.constant.ExceptionStatusConstant.NOT_FOUND_ADMIN_USER_EXP;
import static com.ifox.platform.common.constant.ExceptionStatusConstant.REPEATED_ADMIN_USER_EXP;

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
            logger.warn(ExceptionUtil.getStackTraceAsString(e));
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
        try {
            AdminUserEO adminUserEO = getAdminUserEOByLoginName(loginName);
            return ModelMapperUtil.get().map(adminUserEO, AdminUserDTO.class);
        } catch (NotFoundAdminUserException | RepeatedAdminUserException e) {
            logger.warn(e.getMessage());
        }
        return null;
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
            logger.info(ExceptionUtil.getStackTraceAsString(e));
        }
        if (adminUserEO != null) {
            payload.setIss(env.getProperty("jwt.payload.iss"));
            payload.setIat(new Date());
            payload.setExp(DateTimeUtil.getThreeOclockAMOfTheNextDay());  //过期时间为第二天的凌晨三点钟
            payload.setJti(UUIDUtil.randomUUID());
            payload.setUserId(adminUserEO.getId());
            payload.setLoginName(adminUserEO.getLoginName());
            payload.setHeadPortrait(adminUserEO.getHeadPortrait());

            List<RoleEO> roleEOList = adminUserEO.getRoleEOList();
            List<String> roleIdList = new ArrayList<>();
            for (RoleEO role : roleEOList) {
                if (role.getStatus() == RoleEO.RoleEOStatus.ACTIVE)
                    roleIdList.add(role.getId());
            }
            int size = roleIdList.size();
            payload.setRoleIdList(roleIdList.toArray(new String[size]));
        }
        return payload;
    }

    /**
     * 分页查询
     * @param pageRequest 分页条件
     * @return Page<AdminUserVO>
     */
    @Override
    public Page<AdminUserDTO> page(AdminUserPageRequest pageRequest) {
        SimplePage simplePage = pageRequest.convertSimplePage();

        AdminUserQueryRequest queryRequest = ModelMapperUtil.get().map(pageRequest, AdminUserQueryRequest.class);
        List<QueryProperty> queryPropertyList = generateQueryPropertyList(queryRequest);

        List<SimpleOrder> simpleOrderList = pageRequest.getSimpleOrderList();

        QueryConditions queryConditions = new QueryConditions(null, queryPropertyList, simpleOrderList);

        Page<AdminUserEO> adminUserEOPage = pageByQueryConditions(simplePage, queryConditions);

        return AdminUserEOMapDTO.mapPage(adminUserEOPage);
    }

    /**
     * 列表查询
     * @param queryRequest 查询条件
     * @return List<AdminUserDTO>
     */
    @Override
    public List<AdminUserDTO> list(AdminUserQueryRequest queryRequest) {
        List<QueryProperty> queryPropertyList = generateQueryPropertyList(queryRequest);
        List<AdminUserEO> adminUserEOList = listByQueryProperty(queryPropertyList);
        return ModelMapperUtil.get().map(adminUserEOList, new TypeToken<List<AdminUserDTO>>() {}.getType());
    }

    /**
     * 删除多个用户
     * @param ids ID
     */
    @Override
    @Transactional
    public void delete(String[] ids) throws NotFoundAdminUserException, BuildinSystemException {
        for (String id : ids) {
            AdminUserEO adminUserEO = get(id);
            if (adminUserEO == null) {
                throw new NotFoundAdminUserException(NOT_FOUND_ADMIN_USER_EXP, "用户不存在");
            } else if(adminUserEO.getBuildinSystem()) {
                throw new BuildinSystemException(BUILDIN_SYSTEM_EXP, "系统内置用户，不允许删除");
            } else {
                deleteByEntity(adminUserEO);
            }
        }
    }

    /**
     * 根据请求生成查询条件
     * @param queryRequest 请求
     * @return List<QueryProperty>
     */
    private List<QueryProperty> generateQueryPropertyList(AdminUserQueryRequest queryRequest){
        List<QueryProperty> queryPropertyList = new ArrayList<>();

        String loginName = queryRequest.getLoginName();
        if (!StringUtils.isEmpty(loginName)) {
            String appendLoginName = "%" + loginName + "%";
            QueryProperty queryLoginName = new QueryProperty("loginName", EnumDao.Operation.LIKE, appendLoginName);
            queryPropertyList.add(queryLoginName);
        }

        AdminUserEO.AdminUserEOStatus status = queryRequest.getStatus();
        if (status != null) {
            QueryProperty queryStatus = new QueryProperty("status", EnumDao.Operation.EQUAL, status);
            queryPropertyList.add(queryStatus);
        }

        Boolean buildinSystem = queryRequest.getBuildinSystem();
        if (buildinSystem != null) {
            QueryProperty queryBuildinSystem = new QueryProperty("buildinSystem", EnumDao.Operation.EQUAL, buildinSystem);
            queryPropertyList.add(queryBuildinSystem);
        }
        return queryPropertyList;
    }

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return 用户信息EO
     */
    private AdminUserEO getAdminUserEOByLoginName(String loginName) throws NotFoundAdminUserException, RepeatedAdminUserException {
        QueryProperty queryProperty = new QueryProperty("loginName", EnumDao.Operation.EQUAL, loginName);
        List<QueryProperty> queryPropertyList = new ArrayList<>();
        queryPropertyList.add(queryProperty);
        List<AdminUserEO> adminUserEOList = listByQueryProperty(queryPropertyList);
        if (CollectionUtils.isEmpty(adminUserEOList)) {
            throw new NotFoundAdminUserException(NOT_FOUND_ADMIN_USER_EXP, "为找到此用户, loginName:" + loginName);
        }
        if (adminUserEOList.size() > 1) {
            throw new RepeatedAdminUserException(REPEATED_ADMIN_USER_EXP, "数据库存在重复用户名, loginName:" + loginName);
        }
        return adminUserEOList.get(0);
    }

}
