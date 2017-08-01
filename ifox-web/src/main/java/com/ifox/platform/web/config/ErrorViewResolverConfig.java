package com.ifox.platform.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        logger.info("统一异常处理, status:{}", status);

        ModelAndView modelAndView;
        if (status == HttpStatus.NOT_FOUND) {
            modelAndView = new ModelAndView("/404");
            modelAndView.addObject("error", "反正我没有找到");
            return modelAndView;
        } else if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
            modelAndView = new ModelAndView("/500");
            modelAndView.addObject("error", "我错了");
            return modelAndView;
        }

        modelAndView = new ModelAndView("/error");
        modelAndView.addObject("error", "错误代码:" + status);
        return modelAndView;
    }
}
