package com.ifox.platform.file.interceptor;

import com.ifox.platform.common.http.OptionsRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 *
 * @author Yeager
 */
public class OptionsRequestInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(OptionsRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("预检请求拦截器 OptionsRequestInterceptor --> preHandle, IP:{}, URL:{}", request.getRemoteHost(), requestURI);

        //预检请求(用于处理跨域访问的复杂请求)
        boolean success = OptionsRequestHandler.handleOptions(request, response);
        if (success) {
            logger.info("OPTIONS预检请求通过");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
