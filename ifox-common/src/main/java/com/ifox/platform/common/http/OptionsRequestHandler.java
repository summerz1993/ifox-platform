package com.ifox.platform.common.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

public class OptionsRequestHandler {

    /**
     * 预检请求(用于处理跨域访问的复杂请求)
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return boolean
     */
    public static boolean handleOptions(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            response.setStatus(SUCCESS);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,DELETE,PUT,OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "api-version, Authorization, Content-Type");
            return true;
        } else {
            return false;
        }
    }
}