package com.ifox.platform.common.exception;

public class EmailException extends ApplicationException {
    public EmailException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
