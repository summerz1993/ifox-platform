package com.ifox.platform.file.rest;

import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.utility.common.UUIDUtil;
import com.ifox.platform.utility.datetime.DateTimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件服务控制器
 */
@Controller("fileController")
@RequestMapping("/file")
@Api(tags = "文件服务")
public class FileController extends BaseController {

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation("单文件上传")
    @ApiResponses({ @ApiResponse(code = 401, message = "没有文件操作权限")})
    public @ResponseBody BaseResponse upload(@RequestParam("file") MultipartFile file, String absolutePath) {
        String uuid = UUIDUtil.randomUUID();
        logger.info("单文件上传 absolutePath:{}, uuid:{}", absolutePath, uuid);

        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String absolutePath = env.getProperty("file-service.save-path");
//        String absolutePath = "D:";
        String fileName = uuid + ext;
        String currentDateAsString = DateTimeUtil.getCurrentDateAsString(null);

//        absolutePath.endsWith("/")
        String finalPathString = absolutePath + currentDateAsString + File.separator + fileName;
        File finalPath = new File(finalPathString);
        File parentFile = finalPath.getParentFile();
        if (!parentFile.canWrite()) {
            boolean writable = parentFile.setWritable(true);
            if (!writable) {
                logger.info("没有文件操作权限 finalPathString:{}, uuid:{}", finalPathString, uuid);
                return unauthorizedBaseResponse("没有文件操作权限");
            }
        }
        if (!parentFile.exists())
            parentFile.mkdir();

        try {
            file.transferTo(finalPath);
        } catch (IOException e) {
            logger.warn("保存文件异常 finalPathString:{}, uuid:{}", finalPathString, uuid);
            return serverExceptionBaseResponse();
        }

        logger.info("上传成功 finalPathString:{}, uuid:{}", finalPathString, uuid);
        return successBaseResponse(finalPathString);
    }

}
