package com.ifox.platform.utility.common;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.io.UnsupportedEncodingException;


public class EncodeUtilTest {

    @Test
    public void testBase64Converter() throws UnsupportedEncodingException {
        byte[] b = Base64.decodeBase64("eyJyb2xlSWRMaXN0IjpbXSwibG9naW5OYW1lIjoiYWRtaW4iLCJpc3MiOiJ3d3cueWVhZ2VyLnZpcCIsImV4cCI6MTUwMDcxNTQyNiwiaWF0IjoxNTAwNzE1MzY2LCJ1c2VySWQiOiIxIiwianRpIjoiMjU0ODJjNTE5ZGEyNDBmYzgyYTZjYWMwOWZkNTRhMGEifQ");
        String s = new String(b, "UTF-8");
        System.out.println(s);
    }

}
