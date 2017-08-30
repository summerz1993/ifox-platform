package com.ifox.platform.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一异常处理类
 * @author Yeager
 */
@Configuration
public class ErrorViewResolverConfig implements ErrorViewResolver {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        logger.info("统一异常处理, status:{}", status);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("URL", env.getProperty("ifox-web.login-url"));
        modelAndView.addObject("URLName", "重新登陆");

        if (status == HttpStatus.NOT_FOUND) {
            modelAndView.setViewName("/404");
            modelAndView.addObject("error", "亲，没有找到呢");
            return modelAndView;
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            modelAndView.setViewName("/500");
            modelAndView.addObject("error", "不好意思，出错啦");
            return modelAndView;
        }

        modelAndView = new ModelAndView("/error");
        modelAndView.addObject("error", "错误代码:" + status);
        return modelAndView;
    }
}
