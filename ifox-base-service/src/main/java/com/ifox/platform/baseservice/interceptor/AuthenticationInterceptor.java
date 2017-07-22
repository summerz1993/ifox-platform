package com.ifox.platform.baseservice.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;

import static com.ifox.platform.common.constant.RestStatusConstant.UNAUTHORIZED;

/**
 * 认证拦截器
 *
 * @author Yeager
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("认证拦截器 AuthenticationInterceptor --> preHandle, IP:{}, URL:{}", request.getRemoteHost(), request.getRequestURI());

        // 1 检查token是否有效
        String token = request.getHeader("Authorization");
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWTUtil.verifyToken(token, env.getProperty("jwt.secret"));
        } catch (Exception e) {
            logger.error(ExceptionUtil.getStackTraceAsString(e));
            response.setStatus(UNAUTHORIZED);
            logger.info("认证失败");
            return false;
        }

        // 2 检查资源是否存在
        // 查询MenuPermissionEO -> url字段，对应RequestURI
        // 2-1 不存在，返回404
        // 2-2 存在，查询出来的MenuPermissionEO，得到所属的ResourceEO,走第三步

        // 3-1 公共资源 直接访问
        // return true;

        // 3-2 角色资源 检查该用户角色是否拥有对应的角色
        // 根据2-2查询出来的MenuPermissionEO得到所有roleID，判断当前token中是否包含此roleID

        // 3-3 私人资源 检查访问的资源是不是所属该用户  --- 此步骤建议在controller中实现


        logger.info("认证通过");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
