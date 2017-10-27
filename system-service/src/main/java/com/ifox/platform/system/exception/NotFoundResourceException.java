package com.ifox.platform.system.exception;

import com.ifox.platform.common.exception.ApplicationException;

public class NotFoundResourceException extends ApplicationException {

    public NotFoundResourceException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
