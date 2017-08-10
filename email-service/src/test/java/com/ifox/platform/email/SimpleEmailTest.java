package com.ifox.platform.email;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleEmailTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testSendSimpleEmail() {
        String url = "http://localhost:8084/email/sendSimpleEmail";
        String paramJson = "{\n" +
            "  \"cc\": [\n" +
            "    \"1807804098@qq.com\"\n" +
            "  ],\n" +
            "  \"subject\": \"测试邮件发送接口\",\n" +
            "  \"text\": \"邮件\",\n" +
            "  \"to\": [\n" +
            "    \"425552601@qq.com\"\n" +
            "  ],\n" +
            "  \"token\": \"1234567890Yeager\"\n" +
            "}";
        String body = HttpRequest.post(url).send(paramJson).body();

        logger.info(body);

    }

}
