package com.ifox.platform.adminuser.service.impl;

import com.ifox.platform.adminuser.dto.AdminUserDTO;
import com.ifox.platform.adminuser.exception.NotFoundAdminUserException;
import com.ifox.platform.adminuser.exception.RepeatedAdminUserException;
import com.ifox.platform.adminuser.request.AdminUserQueryRequest;
import com.ifox.platform.adminuser.response.AdminUserVO;
import com.ifox.platform.adminuser.service.AdminUserService;
import com.ifox.platform.baseservice.impl.GenericServiceImpl;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.PageRequest;
import com.ifox.platform.dao.adminuser.AdminUserDao;
import com.ifox.platform.entity.sys.AdminUserEO;
import com.ifox.platform.entity.sys.RoleEO;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 分页查询
     * @param queryRequest 查询条件
     * @param pageRequest 分页条件
     * @return Page<AdminUserVO>
     */
    @Override
    public Page<AdminUserDTO> page(AdminUserQueryRequest queryRequest, PageRequest pageRequest) {
        SimplePage simplePage = new SimplePage();
        simplePage.setPageNo(pageRequest.getPageNo());
        simplePage.setPageSize(pageRequest.getPageSize());

        List<QueryProperty> queryPropertyList = generateQueryPropertyList(queryRequest);

        Page<AdminUserEO> adminUserEOPage = pageByQueryProperty(simplePage, queryPropertyList);
        List<AdminUserEO> adminUserEOList = adminUserEOPage.getContent();
        List<AdminUserDTO> adminUserDTOList = new ArrayList<>();
        for (AdminUserEO userEO :
            adminUserEOList) {
            AdminUserDTO adminUserDTO = new AdminUserVO();
            BeanUtils.copyProperties(userEO, adminUserDTO);
            adminUserDTOList.add(adminUserDTO);
        }

        Page<AdminUserDTO> adminUserDTOPage = new Page<>();
        adminUserDTOPage.setContent(adminUserDTOList);
        adminUserDTOPage.setTotalCount(adminUserEOPage.getTotalCount());
        adminUserDTOPage.setPageSize(adminUserEOPage.getPageSize());
        adminUserDTOPage.setPageNo(adminUserEOPage.getPageNo());

        return adminUserDTOPage;
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
        List<AdminUserDTO> adminUserDTOList = new ArrayList<>();
        for (AdminUserEO userEO :
            adminUserEOList) {
            AdminUserDTO adminUserDTO = new AdminUserVO();
            BeanUtils.copyProperties(userEO, adminUserDTO);
            adminUserDTOList.add(adminUserDTO);
        }
        return adminUserDTOList;
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
            QueryProperty queryLoginName = new QueryProperty("loginName", EnumDao.Operation.LIKE, loginName);
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
