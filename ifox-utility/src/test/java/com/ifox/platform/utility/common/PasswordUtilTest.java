package com.ifox.platform.utility.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码工具测试类
 * @author Yeager
 */
public class PasswordUtilTest {

    Logger logger = LoggerFactory.getLogger(PasswordUtilTest.class);

    String plainPassword = "123456zz";
//    String salt = "123";

    @Test
    public void testEncryptPassword(){
        byte[] bytes = DigestUtil.generateSalt(PasswordUtil.SALT_SIZE);
        String salt = EncodeUtil.encodeHex(bytes);
        String encryptPassword = PasswordUtil.encryptPassword(plainPassword, salt);

        logger.info("盐 : {}", salt);
        logger.info("加密后的密码 : {}", encryptPassword);
    }

    @Test
    public void testValidatePassword(){
        boolean validateResult = PasswordUtil.validatePassword(plainPassword, "0bad6c3e20be52ad", "0bad6c3e20be52ad099c334d48ab5604a897b80475ef1f30540a4baf");
        logger.info("验证密码 : {}", validateResult);
    }

}
