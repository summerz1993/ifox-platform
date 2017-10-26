package com.ifox.platform.system.service.impl;

import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.bean.SimpleOrder;
import com.ifox.platform.common.exception.BuildinSystemException;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.system.dao.RoleRepository;
import com.ifox.platform.system.dto.RoleDTO;
import com.ifox.platform.system.entity.RoleEO;
import com.ifox.platform.system.exception.NotFoundAdminUserException;
import com.ifox.platform.system.modelmapper.RoleEOMapDTO;
import com.ifox.platform.system.request.role.RolePageRequest;
import com.ifox.platform.system.request.role.RoleQueryRequest;
import com.ifox.platform.system.service.RoleService;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.BUILDIN_SYSTEM_EXP;
import static com.ifox.platform.common.constant.ExceptionStatusConstant.NOT_FOUND_ADMIN_USER_EXP;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 分页查询角色
     * @param pageRequest 分页参数
     * @return Page<RoleDTO>
     */
    @Override
    public Page<RoleDTO> page(RolePageRequest pageRequest) {




        SimplePage simplePage = pageRequest.convertSimplePage();

        List<QueryProperty> queryPropertyList = getQueryPropertyList(pageRequest.getName(), pageRequest.getStatus());
        List<SimpleOrder> simpleOrderList = pageRequest.getSimpleOrderList();

        QueryConditions queryConditions = new QueryConditions(null, queryPropertyList, simpleOrderList);

        Page<RoleEO> roleEOPage = pageByQueryConditions(simplePage, queryConditions);

        return RoleEOMapDTO.mapPage(roleEOPage);
    }

    /**
     * 删除多个角色
     * @param ids ID
     */
    @Override
    public void delete(String[] ids) throws NotFoundAdminUserException, BuildinSystemException {
        for (String id : ids) {
            RoleEO roleEO = roleRepository.findOne(id);
            if (roleEO == null) {
                throw new NotFoundAdminUserException(NOT_FOUND_ADMIN_USER_EXP, "角色不存在");
            } else if(roleEO.getBuildinSystem()) {
                throw new BuildinSystemException(BUILDIN_SYSTEM_EXP, "系统内置角色，不允许删除");
            } else {
                roleRepository.delete(roleEO);
            }
        }
    }

    /**
     * 通过identifier查询角色
     * @param identifier identifier
     * @return RoleDTO
     */
    @Override
    public RoleDTO getByIdentifier(String identifier) {
        List<RoleEO> roleEOList = roleRepository.findByIdentifier(identifier);
        if (!CollectionUtils.isEmpty(roleEOList)) {
            RoleEO roleEO = roleEOList.get(0);
            return ModelMapperUtil.get().map(roleEO, RoleDTO.class);
        }
        return null;
    }

    /**
     * list查询
     * @param queryRequest RoleQueryRequest
     * @return List<RoleDTO>
     */
    @Override
    public List<RoleDTO> list(RoleQueryRequest queryRequest) {
        List<RoleEO> roleEOList = roleRepository.findByNameLikeAndStatusEquals(queryRequest.getName(), queryRequest.getStatus());
        return ModelMapperUtil.get().map(roleEOList, new TypeToken<List<RoleDTO>>() {}.getType());
    }

}
