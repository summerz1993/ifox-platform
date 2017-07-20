package com.ifox.platform.adminuser.exception;

/**
 * 重复用户异常
 */
public class RepeatedAdminUserException extends Exception {
    public RepeatedAdminUserException(String message) {
        super(message);
    }
}
