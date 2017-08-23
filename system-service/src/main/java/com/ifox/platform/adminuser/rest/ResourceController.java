package com.ifox.platform.adminuser.rest;

import com.google.common.reflect.TypeToken;
import com.ifox.platform.adminuser.dto.ResourceDTO;
import com.ifox.platform.adminuser.request.resource.ResourcePageRequest;
import com.ifox.platform.adminuser.request.resource.ResourceSaveRequest;
import com.ifox.platform.adminuser.request.resource.ResourceUpdateRequest;
import com.ifox.platform.adminuser.response.ResourceVO;
import com.ifox.platform.adminuser.service.ResourceService;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.PageInfo;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;
import com.ifox.platform.entity.common.ResourceEO;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Api(tags = "资源管理")
@Controller
@RequestMapping(value = "/resource", headers = {"api-version=1.0", "Authorization"})
public class ResourceController extends BaseController<ResourceVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourceService resourceService;

    @ApiOperation("添加资源")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponse save(@ApiParam @RequestBody ResourceSaveRequest resource){
        logger.info("保存资源：{}", resource.toString());

        ResourceEO resourceEO = ModelMapperUtil.get().map(resource, ResourceEO.class);
        resourceService.save(resourceEO);

        logger.info(successSave);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除资源")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 400, message = "无效请求：ids为空"),
        @ApiResponse(code = 404, message = "资源不存在")})
    public @ResponseBody
    BaseResponse delete(@ApiParam @RequestBody String[] ids){
        logger.info("删除资源:{}", Arrays.toString(ids));

        if (ids.length == 0){
            logger.info("无效请求：ids为空");
            return invalidRequestBaseResponse();
        }

        try {
            resourceService.deleteMulti(ids);
        } catch (IllegalArgumentException e) {
            return notFoundBaseResponse("资源不存在");
        }

        logger.info(successDelete);
        return successDeleteBaseResponse();
    }

    @ApiOperation("获取指定资源")
    @RequestMapping(value = "/get/{resourceId}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 404, message = "资源不存在")})
    @SuppressWarnings("unchecked")
    public @ResponseBody
    OneResponse<ResourceVO> get(@ApiParam @PathVariable(name = "resourceId") String id){
        logger.info("查询单个指定资源：{}", id);

        ResourceEO resourceEO = resourceService.get(id);
        if (resourceEO == null){
            logger.info("资源不存在：{}", id);
            return super.notFoundOneResponse("资源不存在");
        }

        ResourceVO resourceVO = ModelMapperUtil.get().map(resourceEO, ResourceVO.class);
        logger.info(successQuery);

        return successQueryOneResponse(resourceVO);
    }

    @ApiOperation("更新资源")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({@ApiResponse(code = 404, message = "资源不存在")})
    public @ResponseBody
    BaseResponse update(@ApiParam @RequestBody ResourceUpdateRequest resource){
        logger.info("更新资源：{}", resource);

        String id = resource.getId();
        ResourceEO resourceEO = resourceService.get(id);
        if (resourceEO == null){
            logger.info("资源不存在：{}", id);
            return super.notFoundOneResponse("资源不存在");
        }

        ModelMapperUtil.get().map(resource, resourceEO);
        resourceService.update(resourceEO);
        logger.info(successUpdate);

        return successUpdateBaseResponse();
    }

    @ApiOperation("分页查询资源")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody
    @SuppressWarnings("unchecked")
    PageResponse<ResourceVO> page(@ApiParam @RequestBody ResourcePageRequest pageRequest){
        logger.info("分页查询资源：", pageRequest);

        Page<ResourceDTO> resourceDTOPage = resourceService.page(pageRequest);
        List<ResourceDTO> resourceDTOList = resourceDTOPage.getContent();

        PageInfo pageInfo = resourceDTOPage.convertPageInfo();
        List<ResourceVO> resourceVOList = ModelMapperUtil.get().map(resourceDTOList, new TypeToken<List<ResourceVO>>() {}.getType());

        logger.info(successQuery);
        return successQueryPageResponse(pageInfo, resourceVOList);
    }
}
