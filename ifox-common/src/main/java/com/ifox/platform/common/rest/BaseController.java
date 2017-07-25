package com.ifox.platform.common.rest;

import static com.ifox.platform.common.constant.RestStatusConstant.NOT_FOUND;

/**
 * Created by yezhang on 7/14/2017.
 */
public class BaseController {

    protected BaseResponse notFoundBaseResponse(String desc){
        return new BaseResponse(NOT_FOUND, desc);
    }

    @SuppressWarnings("unchecked")
    protected OneResponse notFoundOneResponse(String desc){
        return new OneResponse(NOT_FOUND, desc, null);
    }

}
