package com.ifox.platform.email.rest;

import com.ifox.platform.common.exception.EmailException;
import com.ifox.platform.common.rest.BaseController;
import com.ifox.platform.common.rest.response.BaseResponse;
import com.ifox.platform.email.request.SimpleEmailRequest;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.ifox.platform.common.constant.ExceptionStatusConstant.EMAIL_EXP;

/**
 * 邮件控制器
 * @author Yeager
 */
@Controller("emailController")
@RequestMapping(value = "/email", headers = {"api-version=1.0", "Authorization"})
@Api(tags = "邮件服务")
public class EmailController extends BaseController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment env;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 发送简单邮件
     * @param request SimpleEmailRequest
     * @return BaseResponse
     */
    @RequestMapping(value = "/sendSimpleEmail", method = RequestMethod.POST)
    @ApiOperation("发送简单邮件")
    @ApiResponses({ @ApiResponse(code = 200, message = "发送成功"),
        @ApiResponse(code = 481, message = "Token校验失败"),
        @ApiResponse(code = 500, message = "服务器错误")})
    public @ResponseBody BaseResponse sendSimpleEmail(@ApiParam @RequestBody SimpleEmailRequest request, @RequestHeader("Authorization") String token, HttpServletResponse response) {
        logger.info("sendSimpleEmail 发送邮件");
        //校验Token
        String applicationToken = env.getProperty("email-service.token");
        if (!applicationToken.equals(token)){
            logger.info("发送失败：token校验失败");
            return tokenErrorBaseResponse(response);
        }

        try {
            send(request);
        } catch (EmailException e) {
            logger.error(e.getMessage());
            logger.info("发送失败：服务器异常");
            return serverExceptionBaseResponse(response);
        }

        logger.info("发送成功");
        return successBaseResponse("发送成功");
    }

    private void send(@RequestBody SimpleEmailRequest request) throws EmailException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(request, message);

            message.setFrom(env.getProperty("spring.mail.username"));

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailException(EMAIL_EXP, "邮件发送异常");
        }

    }

}
