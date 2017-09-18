package com.ifox.platform.baseservice.interceptor;

import com.ifox.platform.dao.sys.MenuPermissionDao;
import com.ifox.platform.dao.sys.ResourceDao;
import com.ifox.platform.dao.sys.RoleDao;
import com.ifox.platform.entity.common.ResourceEO;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ifox.platform.common.constant.RestStatusConstant.NOT_FOUND;
import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;
import static com.ifox.platform.common.constant.RestStatusConstant.UNAUTHORIZED;

/**
 * 认证拦截器
 *
 * @author Yeager
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private Environment env;

    @Autowired
    private ResourceDao resourceDao;

    @Autowired
    private MenuPermissionDao menuPermissionDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        logger.info("认证拦截器 AuthenticationInterceptor --> preHandle, IP:{}, URL:{}", request.getRemoteHost(), requestURI);

        //预检请求(用于处理跨域访问的复杂请求)
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            response.setStatus(SUCCESS);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,DELETE,PUT,OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "api-version, Authorization, Content-Type");
            logger.info("OPTIONS预检请求通过");
            return false;
        }
if (true) return true;
        //例子:/role/get/8ab2a8c55df468ed015df47e818a0002
        String[] splitURI = requestURI.split("/");
        String controller = splitURI[1];
        //TODO:此处可做缓存
        ResourceEO resourceEO = resourceDao.getByController(controller);
        //1 资源不存在，返回404
        if (resourceEO == null) {
            logger.info("资源不存在或未定义");
            response.setStatus(NOT_FOUND);
            return false;
        }

        //2 公共资源，不用验证权限
        if (ResourceEO.ResourceEOType.PUBLIC == resourceEO.getType()) {
            logger.info("公共资源");
            return true;
        }

        //3 鉴权，检查token是否有效
        String token = request.getHeader("Authorization");
        try {
            JWTUtil.verifyToken(token, env.getProperty("jwt.secret"));
        } catch (Exception e) {
            response.setStatus(UNAUTHORIZED);
            logger.info("认证失败:token校验失败");
            return false;
        }

        //4 角色资源 检查该用户角色是否拥有对应的权限
        //检查URL最后是不是UUID
        String isUUID = splitURI[splitURI.length - 1];
        int uuidLength = 32;
        String menuPermissionURL = requestURI;
        if (isUUID.length() == uuidLength) {
            //如果是UUID则舍去, 例子:/role/get
            menuPermissionURL = requestURI.substring(0, requestURI.length() - uuidLength - 1);
        }

        String payload = JWTUtil.getPayloadStringByToken(token);
        Any payLoadAny = JsonIterator.deserialize(payload);
        String[] roleIdList = ArrayUtils.toArray(payLoadAny.get("roleIdList").toString());
        //TODO:此处可做缓存
        MenuPermissionEO menuPermissionEO = menuPermissionDao.getByURL(menuPermissionURL);
        if (menuPermissionEO == null || menuPermissionEO.getStatus() == MenuPermissionEO.MenuEOStatus.INVALID) {
            logger.info("菜单权限不存在或未定义或状态无效");
            response.setStatus(NOT_FOUND);
            return false;
        }
        Integer count = roleDao.countByRoleIdListAndMenuPermission(roleIdList, menuPermissionEO.getId());
        if (count == null || count.equals(0)) {
            response.setStatus(UNAUTHORIZED);
            logger.info("认证失败:角色资源无对应权限");
            return false;
        }

        //5 私人资源 检查访问的资源是不是所属该用户  --- 此步骤建议在controller中实现

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
