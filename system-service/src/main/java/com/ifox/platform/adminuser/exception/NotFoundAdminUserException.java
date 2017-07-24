package com.ifox.platform.adminuser.exception;

import com.ifox.platform.common.exception.ApplicationException;

/**
 * 为找到用户异常
 */
public class NotFoundAdminUserException extends ApplicationException {

    public NotFoundAdminUserException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
