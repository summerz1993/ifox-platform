package com.ifox.platform.system.service.impl;

import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.request.PageRequest;
import com.ifox.platform.jpa.converter.PageRequestConverter;
import com.ifox.platform.jpa.converter.SpringDataPageConverter;
import com.ifox.platform.system.dao.ResourceRepository;
import com.ifox.platform.system.entity.ResourceEO;
import com.ifox.platform.system.exception.NotFoundResourceException;
import com.ifox.platform.system.request.resource.ResourcePageRequest;
import com.ifox.platform.system.request.resource.ResourceUpdateRequest;
import com.ifox.platform.system.service.ResourceService;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.NOT_FOUND_RESOURCE_EXP;

@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public SimplePage<ResourceEO> page(ResourcePageRequest pageRequest) {
        Pageable pageable = PageRequestConverter.convertToSpringDataPageable(pageRequest);
        Page<ResourceEO> page = resourceRepository.findAllByNameLikeAndTypeEquals(pageRequest.getName(), pageRequest.getType(), pageable);
        return new SpringDataPageConverter<ResourceEO>().convertToSimplePage(page);
    }

    @Override
    @Transactional
    @Modifying
    public void save(ResourceEO resourceEO) {
        resourceRepository.save(resourceEO);
    }

    @Override
    @Transactional
    @Modifying
    public void deleteMulti(String[] ids) throws NotFoundResourceException {
        for (String id : ids) {
            try {
                resourceRepository.delete(id);
            } catch (EmptyResultDataAccessException emptyExc) {
                throw new NotFoundResourceException(NOT_FOUND_RESOURCE_EXP, "资源不存在");
            }
        }
    }

    @Override
    public ResourceEO get(String id) {
        return resourceRepository.findOne(id);
    }

    @Override
    @Transactional
    @Modifying
    public void update(ResourceUpdateRequest resourceUpdateRequest) {
        ResourceEO resourceEO = resourceRepository.findOne(resourceUpdateRequest.getId());
        ModelMapperUtil.get().map(resourceUpdateRequest, resourceEO);
    }

    @Override
    public List<ResourceEO> listAll() {
        return resourceRepository.findAll();
    }

    @Override
    public ResourceEO getByController(String controller) {
        return resourceRepository.findFirstByControllerEquals(controller);
    }

}
