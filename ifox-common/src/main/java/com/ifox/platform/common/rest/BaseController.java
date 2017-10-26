package com.ifox.platform.common.rest;

import com.ifox.platform.common.rest.response.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.*;

/**
 * Created by yezhang on 7/14/2017.
 */
public class BaseController<T> {

    protected String successSave = "保存成功";

    protected String successDelete = "删除成功";

    protected String successUpdate = "更新成功";

    protected String successQuery = "查询成功";

    //--------------- success ----------------

    protected BaseResponse successSaveBaseResponse(){
        return new BaseResponse(SUCCESS, successSave);
    }

    protected BaseResponse successDeleteBaseResponse(){
        return new BaseResponse(SUCCESS, successDelete);
    }

    protected BaseResponse successUpdateBaseResponse(){
        return new BaseResponse(SUCCESS, successUpdate);
    }

    protected BaseResponse successQueryBaseResponse(){
        return new BaseResponse(SUCCESS, successQuery);
    }

    @SuppressWarnings("unchecked")
    protected OneResponse successQueryOneResponse(T t){
        return new OneResponse(SUCCESS, successQuery, t);
    }

    @SuppressWarnings("unchecked")
    protected PageResponse successQueryPageResponse(PageResponseDetail pageResponseDetail, List<T> data) {
        return new PageResponse(SUCCESS, successQuery, pageResponseDetail, data);
    }

    @SuppressWarnings("unchecked")
    protected MultiResponse successQueryMultiResponse(List<T> data) {
        return new MultiResponse(SUCCESS, successQuery, data);
    }

    protected BaseResponse successBaseResponse(String desc){
        return new BaseResponse(SUCCESS, desc);
    }

    //--------------- token error ----------------

    protected BaseResponse tokenErrorBaseResponse(HttpServletResponse response) {
        response.setStatus(TOKEN_ERROR);
        return new BaseResponse(TOKEN_ERROR, "token错误");
    }

    //--------------- server error ----------------

    protected BaseResponse serverExceptionBaseResponse(HttpServletResponse response) {
        response.setStatus(SERVER_EXCEPTION);
        return new BaseResponse(SERVER_EXCEPTION, "服务器异常");
    }

    //--------------- not found ----------------

    protected BaseResponse notFoundBaseResponse(String desc, HttpServletResponse response){
        response.setStatus(NOT_FOUND);
        return new BaseResponse(NOT_FOUND, desc);
    }

    @SuppressWarnings("unchecked")
    protected OneResponse notFoundOneResponse(String desc, HttpServletResponse response){
        response.setStatus(NOT_FOUND);
        return new OneResponse(NOT_FOUND, desc, null);
    }

    //--------------- 400 ----------------

    protected BaseResponse invalidRequestBaseResponse(HttpServletResponse response){
        response.setStatus(INVALID_REQUEST);
        return new BaseResponse(INVALID_REQUEST, "无效请求");
    }

    //--------------- 401 ----------------

    protected BaseResponse unauthorizedBaseResponse(String desc, HttpServletResponse response) {
        response.setStatus(UNAUTHORIZED);
        return new BaseResponse(UNAUTHORIZED, StringUtils.isEmpty(desc) ? "未授权访问" : desc);
    }

    //--------------- fail ----------------

    protected BaseResponse notSupportFileTypeBaseResponse(HttpServletResponse response){
        response.setStatus(NOT_SUPPORT_FILE_TYPE);
        return new BaseResponse(NOT_SUPPORT_FILE_TYPE, "不支持的文件类型");
    }

    protected BaseResponse notSupportServiceNameBaseResponse(HttpServletResponse response){
        response.setStatus(NOT_SUPPORT_SERVICE_NAME);
        return new BaseResponse(NOT_SUPPORT_SERVICE_NAME, "不支持的服务名称");
    }

    //--------------- delete self error ----------------

    protected BaseResponse deleteSelfErrorBaseResponse(HttpServletResponse response){
        response.setStatus(DELETE_SELF_ERROR);
        return new BaseResponse(DELETE_SELF_ERROR, "不允许删除自身账号");
    }

    //--------------- buildin system ----------------

    protected BaseResponse deleteBuildinSystemErrorBaseResponse(String desc, HttpServletResponse response){
        response.setStatus(BUILD_IN_SYSTEM_CAN_NOT_DELETE);
        return new BaseResponse(BUILD_IN_SYSTEM_CAN_NOT_DELETE, desc);
    }

    //--------------- existed identifier ----------------

    protected BaseResponse existedIdentifierBaseResponse(String desc, HttpServletResponse response){
        response.setStatus(EXISTED_IDENTIFIER);
        return new BaseResponse(EXISTED_IDENTIFIER, desc);
    }

}
