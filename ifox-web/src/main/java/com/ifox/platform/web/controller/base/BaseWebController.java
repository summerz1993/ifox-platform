package com.ifox.platform.web.controller.base;

import com.github.kevinsawicki.http.HttpRequest;
import com.ifox.platform.utility.common.ExceptionUtil;
import com.ifox.platform.utility.jwt.JWTUtil;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.ifox.platform.common.constant.RestStatusConstant.SUCCESS;

public class BaseWebController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    protected String verifyToken(HttpServletRequest request, Model model, String view) {
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            String cookieName = cookie.getName();
            if ("token".equals(cookieName)) {
                token = cookie.getValue();
                break;
            }
        }

        HttpRequest httpRequest = getTokenHttpRequest(token);
        int code = httpRequest.code();
        String body = httpRequest.body();
        logger.info("code = {}, body = {}", code, body);

        Any bodyAny = JsonIterator.deserialize(body);
        int status = bodyAny.get("status").toInt();

        if ( SUCCESS != code || SUCCESS != status) {
            logger.info("token校验失败");
            model.addAttribute("error", "无效请求");
            model.addAttribute("URL", env.getProperty("ifox-web.login-url"));
            model.addAttribute("URLName", "重新登陆");
            return "/error";
        }

        //token校验通过
        logger.info("token校验成功");

        String payload = JWTUtil.getPayloadStringByToken(token);

        Any payLoadAny = JsonIterator.deserialize(payload);
        String userId = payLoadAny.get("userId").toString();
        String loginName = payLoadAny.get("loginName").toString();
        String headPortrait = payLoadAny.get("headPortrait").toString();

        model.addAttribute("userId", userId);
        model.addAttribute("loginName", loginName);
        processHeadPortrait(headPortrait, model);

        return view;
    }

    private void processHeadPortrait(String headPortrait, Model model) {
        if (StringUtils.isEmpty(headPortrait)) {
            model.addAttribute("headPortrait", "");
        } else {
            String headPortraitURL = env.getProperty("ifox-web.file-service-base-url") + "file/get?fileType=PICTURE&path=";
            try {
                headPortrait = URLEncoder.encode(headPortrait, "UTF-8");
                model.addAttribute("headPortrait", headPortraitURL + headPortrait);
            } catch (UnsupportedEncodingException e) {
                ExceptionUtil.getStackTraceAsString(e);
                model.addAttribute("headPortrait", "");
                logger.warn("headPortraitURL编码异常");
            }
        }
    }

    private HttpRequest getTokenHttpRequest(String token) {
        String verifyTokenUrl = "adminUser/verifyToken";
        String url = env.getProperty("ifox-web.admin-user-service-base-url") + verifyTokenUrl;
        return HttpRequest.post(url).header("api-version", "1.0").form("token", token);
    }

}
