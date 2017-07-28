package com.ifox.platform.utility.http;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtilTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testHttpRequest() {

        String url = "http://localhost:8081/adminUser/get/1234";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlSWRMaXN0IjpbXSwibG9naW5OYW1lIjoiYWRtaW4iLCJpc3MiOiJ3d3cueWVhZ2VyLnZpcCIsImV4cCI6MTUwMTIyNzU0MiwiaWF0IjoxNTAxMjIxNTQyLCJ1c2VySWQiOiI4YWIyYThjNTVkNjM1ZDZiMDE1ZDYzNWU1MjIyMDAwMCIsImp0aSI6Ijc5Yzg1NDU5YWMxZDQyZDdhMTU1ZDg0N2IwNWUzMTExIn0.BIfJDad3xBMnYZlH5M_3_-P_pvBHLEJ2qMhBPjOIgTQ";

        String body = HttpRequest.get(url).header("Authorization", token).header("api-version", "1.0").body();

        logger.info("body : {}", body);

    }

}
