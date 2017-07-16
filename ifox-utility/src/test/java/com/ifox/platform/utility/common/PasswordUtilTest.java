package com.ifox.platform.utility.common;

import org.junit.Test;

/**
 * 密码工具测试类
 * @author Yeager
 */
public class PasswordUtilTest {

    String plainPassword = "123456zz";

    @Test
    public void testEncryptPassword(){
        String encryptPassword = PasswordUtil.encryptPassword(plainPassword);
        System.out.println("加密后的密码 : " + encryptPassword);
    }

    @Test
    public void testValidatePassword(){
        boolean validateResult = PasswordUtil.validatePassword(plainPassword, "eed116c4a1adaa596b1e94e2638a9e4160f887190f14c1e4d5229444");
        System.out.println("验证密码 : " + validateResult);
    }

}
