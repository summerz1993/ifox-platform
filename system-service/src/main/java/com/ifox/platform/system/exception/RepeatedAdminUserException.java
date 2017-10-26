package com.ifox.platform.system.exception;

import com.ifox.platform.common.exception.ApplicationException;

/**
 * 重复用户异常
 * @author Yeager
 */
public class RepeatedAdminUserException extends ApplicationException {
    public RepeatedAdminUserException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
