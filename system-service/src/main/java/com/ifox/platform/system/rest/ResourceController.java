package com.ifox.platform.system.rest;

import com.google.common.reflect.TypeToken;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.*;
import com.ifox.platform.system.entity.ResourceEO;
import com.ifox.platform.system.exception.NotFoundResourceException;
import com.ifox.platform.system.request.resource.ResourcePageRequest;
import com.ifox.platform.system.request.resource.ResourceSaveRequest;
import com.ifox.platform.system.request.resource.ResourceUpdateRequest;
import com.ifox.platform.system.response.ResourceVO;
import com.ifox.platform.system.service.ResourceService;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.modelmapper.ModelMapperUtil;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Api(tags = "资源管理")
@Controller
@RequestMapping(value = "/resource", headers = {"api-version=1.0", "Authorization"})
public class ResourceController extends BaseController<ResourceVO> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ResourceService resourceService;

    @ApiOperation("添加资源")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    BaseResponse save(@ApiParam @RequestBody @Validated ResourceSaveRequest resourceSaveRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("保存资源 resourceSaveRequest:{}, uuid:{}", resourceSaveRequest.toString(), uuid);

        ResourceEO resourceEO = ModelMapperUtil.get().map(resourceSaveRequest, ResourceEO.class);
        resourceService.save(resourceEO);

        logger.info(successSave + " uuid:{}", uuid);
        return successSaveBaseResponse();
    }

    @ApiOperation("删除资源")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 400, message = "无效请求：ids为空"),
        @ApiResponse(code = 404, message = "资源不存在")})
    public @ResponseBody
    BaseResponse delete(@ApiParam @RequestBody @NotBlank String[] ids, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("删除资源 ids:{}, uuid:{}", Arrays.toString(ids), uuid);

        if (ids.length == 0){
            logger.info("无效请求,ids为空 uuid:{}", uuid);
            return invalidRequestBaseResponse(response);
        }

        try {
            resourceService.deleteMulti(ids);
        } catch (NotFoundResourceException e) {
            logger.info("资源不存在 uuid:{}", uuid);
            return notFoundBaseResponse("资源不存在", response);
        }

        logger.info(successDelete + " uuid:{}", uuid);
        return successDeleteBaseResponse();
    }

    @ApiOperation("获取指定资源")
    @RequestMapping(value = "/get/{resourceId}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 404, message = "资源不存在")})
    @SuppressWarnings("unchecked")
    public @ResponseBody
    OneResponse<ResourceVO> get(@ApiParam @PathVariable(name = "resourceId") @NotBlank String id, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("查询单个指定资源 id:{}, uuid:{}", id, uuid);

        ResourceEO resourceEO = resourceService.get(id);
        if (resourceEO == null){
            logger.info("资源不存在 id:{}, uuid:{}", id, uuid);
            return super.notFoundOneResponse("资源不存在", response);
        }

        ResourceVO resourceVO = ModelMapperUtil.get().map(resourceEO, ResourceVO.class);
        logger.info(successQuery + " uuid:{}", uuid);

        return successQueryOneResponse(resourceVO);
    }

    @ApiOperation("更新资源")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiResponses({@ApiResponse(code = 404, message = "资源不存在")})
    public @ResponseBody
    BaseResponse update(@ApiParam @RequestBody @Validated ResourceUpdateRequest resourceUpdateRequest, HttpServletResponse response){
        String uuid = UUIDUtil.randomUUID();
        logger.info("更新资源 resourceUpdateRequest:{}, uuid:{}", resourceUpdateRequest, uuid);

        String id = resourceUpdateRequest.getId();
        ResourceEO resourceEO = resourceService.get(id);
        if (resourceEO == null){
            logger.info("资源不存在 id:{}, uuid:{}", id, uuid);
            return super.notFoundOneResponse("资源不存在", response);
        }

        resourceService.update(resourceUpdateRequest);
        logger.info(successUpdate + " uuid:{}", uuid);

        return successUpdateBaseResponse();
    }

    @ApiOperation("分页查询资源")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public @ResponseBody
    @SuppressWarnings("unchecked")
    PageResponse<ResourceVO> page(@ApiParam @RequestBody ResourcePageRequest pageRequest){
        String uuid = UUIDUtil.randomUUID();
        logger.info("分页查询资源 pageRequest:{}, uuid:{}", pageRequest, uuid);

        SimplePage<ResourceEO> resourceEOPage = resourceService.page(pageRequest);

        PageResponseDetail pageResponseDetail = resourceEOPage.convertToPageResponseDetail();
        List<ResourceVO> resourceVOList = ModelMapperUtil.get().map(resourceEOPage.getContent(), new TypeToken<List<ResourceVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryPageResponse(pageResponseDetail, resourceVOList);
    }

    @ApiOperation("获取所有资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @SuppressWarnings("unchecked")
    public @ResponseBody
    MultiResponse<ResourceVO> list(){
        String uuid = UUIDUtil.randomUUID();
        logger.info("获取所有资源 uuid:{}", uuid);

        List<ResourceVO> resourceVOList = ModelMapperUtil.get().map(resourceService.listAll(), new TypeToken<List<ResourceVO>>() {}.getType());

        logger.info(successQuery + " uuid:{}", uuid);
        return successQueryMultiResponse(resourceVOList);
    }
}
