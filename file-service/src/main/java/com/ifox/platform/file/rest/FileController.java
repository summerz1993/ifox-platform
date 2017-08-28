package com.ifox.platform.file.rest;

import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.file.enums.EnumFile;
import com.ifox.platform.file.service.FileService;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * 文件服务控制器
 */
@Controller("fileController")
@RequestMapping(value = "/file", headers = {"api-version=1.0", "Authorization"})
@Api(tags = "文件服务")
public class FileController extends BaseController {

    @Autowired
    private Environment env;

    @Autowired
    private FileService fileService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("单文件上传")
    @ApiResponses({ @ApiResponse(code = 401, message = "没有文件操作权限"),
                    @ApiResponse(code = 484, message = "不支持的文件类型"),
                    @ApiResponse(code = 485, message = "不支持的服务名称")})
    public @ResponseBody BaseResponse upload(@RequestParam("file") MultipartFile file, @RequestParam String serviceName, @RequestParam EnumFile.FileType fileType) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("单文件上传 serviceName:{}, fileType:{}, uuid:{}", serviceName, fileType, uuid);

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        boolean validation = fileService.validateFileType(fileType, ext.substring(1));
        if (!validation) {
            logger.info("不支持的文件类型 uuid:{}", uuid);
            return notSupportFileTypeBaseResponse();
        }

        String[] serviceNameArray = env.getProperty("file-service.service-name").split(",");
        if (!Arrays.asList(serviceNameArray).contains(serviceName)) {
            logger.info("不支持的服务名称 uuid:{}", uuid);
            return notSupportServiceNameBaseResponse();
        }

        String absolutePath = env.getProperty("file-service.save.path");
        String fileName = uuid + ext;
        String currentDateAsString = DateTimeUtil.getCurrentDateAsString(null);
        String relativePath = serviceName + File.separator+ currentDateAsString + File.separator + fileName;
        String finalPathString = absolutePath + relativePath;
        File finalPath = new File(finalPathString);
        File parentFile = finalPath.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdir();

        if (!parentFile.canWrite()) {
            logger.info("没有文件操作权限 finalPathString:{}, uuid:{}", finalPathString, uuid);
            return unauthorizedBaseResponse("没有文件操作权限");
        }

        try {
            file.transferTo(finalPath);
        } catch (IOException e) {
            logger.warn("保存文件异常 finalPathString:{}, uuid:{}", finalPathString, uuid);
            return serverExceptionBaseResponse();
        }

        logger.info("上传成功 finalPathString:{}, uuid:{}", finalPathString, uuid);
        return successBaseResponse(relativePath);
    }

}
