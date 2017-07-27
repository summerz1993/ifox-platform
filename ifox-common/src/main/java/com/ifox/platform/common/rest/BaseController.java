package com.ifox.platform.common.rest;

import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.common.rest.response.MultiResponse;
import com.ifox.platform.common.rest.response.OneResponse;
import com.ifox.platform.common.rest.response.PageResponse;

import java.util.List;

import static com.ifox.platform.common.constant.RestStatusConstant.NOT_FOUND;
import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

/**
 * Created by yezhang on 7/14/2017.
 */
public class BaseController<T> {

    protected String successSave = "保存成功";

    protected String successDelete = "删除成功";

    protected String successUpdate = "更新成功";

    protected String successQuery = "查询成功";

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
    protected PageResponse successQueryPageResponse(PageInfo pageInfo, List<T> data) {
        return new PageResponse(SUCCESS, successQuery, pageInfo, data);
    }

    @SuppressWarnings("unchecked")
    protected MultiResponse successQueryMultiResponse(List<T> data) {
        return new MultiResponse(SUCCESS, successQuery, data);
    }

    protected BaseResponse successBaseResponse(String desc){
        return new BaseResponse(SUCCESS, desc);
    }

    protected BaseResponse notFoundBaseResponse(String desc){
        return new BaseResponse(NOT_FOUND, desc);
    }

    @SuppressWarnings("unchecked")
    protected OneResponse notFoundOneResponse(String desc){
        return new OneResponse(NOT_FOUND, desc, null);
    }

}
