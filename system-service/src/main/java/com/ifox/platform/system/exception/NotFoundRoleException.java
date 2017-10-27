package com.ifox.platform.system.exception;

import com.ifox.platform.common.exception.ApplicationException;

public class NotFoundRoleException extends ApplicationException {

    public NotFoundRoleException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
