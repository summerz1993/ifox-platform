package com.ifox.platform.system.service.impl;

import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.jpa.converter.PageRequestConverter;
import com.ifox.platform.jpa.converter.SpringDataPageConverter;
import com.ifox.platform.system.dao.AdminUserRepository;
import com.ifox.platform.system.entity.AdminUserEO;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundAdminUserException;
import com.ifox.platform.system.exception.RepeatedAdminUserException;
import com.ifox.platform.system.request.adminuser.AdminUserPageRequest;
import com.ifox.platform.system.request.adminuser.AdminUserQueryRequest;
import com.ifox.platform.system.request.adminuser.AdminUserUpdateRequest;
import com.ifox.platform.system.service.AdminUserService;
import com.ifox.platform.system.service.RoleService;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.common.PasswordUtil;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import com.ifox.platform.utility.jwt.JWTPayload;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.BUILDIN_SYSTEM_EXP;
import static com.ifox.platform.common.constant.ExceptionStatusConstant.NOT_FOUND_ADMIN_USER_EXP;

@Service
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements AdminUserService{

    private Logger logger  = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Resource
    private AdminUserRepository adminUserRepository;

    @Resource
    private RoleService roleService;

    /**
     * 验证用户名和密码是否正确
     * @param loginName 用户名
     * @param password 密码
     * @return true:存在 false:不存在
     */
    @Override
    public Boolean validLoginNameAndPassword(String loginName, String password) throws NotFoundAdminUserException, RepeatedAdminUserException {
        AdminUserEO adminUserEO = getByLoginName(loginName);
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
    public AdminUserEO getByLoginName(String loginName) {
        return adminUserRepository.findFirstByLoginNameEquals(loginName);
    }

    /**
     * 根据登录名生成PayLoad
     * @param loginName 登录名
     * @return JWTPayload
     */
    @Override
    public JWTPayload generatePayload(String loginName) {
        JWTPayload payload = new JWTPayload();
        AdminUserEO adminUserEO = getByLoginName(loginName);
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
    public SimplePage<AdminUserEO> page(AdminUserPageRequest pageRequest) {
        Pageable pageable = PageRequestConverter.convertToSpringDataPageable(pageRequest);
        Page<AdminUserEO> page = adminUserRepository.findAllByLoginNameLikeAndStatusEqualsAndBuildinSystemEquals(pageRequest.getLoginName(), pageRequest.getStatus(), pageRequest.getBuildinSystem(), pageable);
        return new SpringDataPageConverter<AdminUserEO>().convertToSimplePage(page);
    }

    /**
     * 列表查询
     * @param queryRequest 查询条件
     * @return List<AdminUserDTO>
     */
    @Override
    public List<AdminUserEO> list(AdminUserQueryRequest queryRequest) {
        return adminUserRepository.findAllByLoginNameLikeAndStatusEqualsAndBuildinSystemEquals(queryRequest.getLoginName(), queryRequest.getStatus(), queryRequest.getBuildinSystem());
    }

    /**
     * 删除多个用户
     * @param ids ID
     */
    @Override
    @Transactional
    @Modifying
    public void delete(String[] ids) throws NotFoundAdminUserException, BuildinSystemException {
        for (String id : ids) {
            AdminUserEO adminUserEO = adminUserRepository.findOne(id);
            if (adminUserEO == null) {
                throw new NotFoundAdminUserException(NOT_FOUND_ADMIN_USER_EXP, "用户不存在");
            } else if(adminUserEO.getBuildinSystem()) {
                throw new BuildinSystemException(BUILDIN_SYSTEM_EXP, "系统内置用户，不允许删除");
            } else {
                adminUserRepository.delete(id);
            }
        }
    }

    @Override
    public AdminUserEO get(String id) {
        return adminUserRepository.findOne(id);
    }

    @Override
    @Transactional
    @Modifying
    public void save(AdminUserEO adminUserEO) {
        adminUserRepository.save(adminUserEO);
    }

    @Override
    @Transactional
    @Modifying
    public AdminUserEO update(AdminUserUpdateRequest updateRequest) {
        AdminUserEO adminUserEO = adminUserRepository.findOne(updateRequest.getId());
        ModelMapperUtil.get().map(updateRequest, adminUserEO);
        String[] checkedRoleArray = updateRequest.getCheckedRole();
        List<RoleEO> roleEOList = new ArrayList<>();
        for (String roleId : checkedRoleArray) {
            RoleEO roleEO = roleService.get(roleId);
            roleEOList.add(roleEO);
        }
        adminUserEO.setRoleEOList(roleEOList);
        return adminUserEO;
    }

    @Override
    @Transactional
    @Modifying
    public void updatePassword(String password, String id) {
        adminUserRepository.updatePassword(password, id);
    }
}
