package com.ifox.platform.utility.common;

/**
 * 密码生成工具
 */
public class PasswordUtil {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    /**
     * 生成安全的密码，根据salt并经过1024次 sha-1 hash
     */
    public static String encryptPassword(String plainPassword, String salt) {
//        byte[] salt = DigestUtil.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt.getBytes(), HASH_INTERATIONS);
        return salt + EncodeUtil.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * @param plainPassword 需要验证的明文密码
     * @param password 数据库密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String salt, String password) {
//        byte[] salt = EncodeUtil.decodeHex(password.substring(0, 16));
        byte[] hashPassword = DigestUtil.sha1(plainPassword.getBytes(), salt.getBytes(), HASH_INTERATIONS);
        return password.equals(salt + EncodeUtil.encodeHex(hashPassword));
    }
}
