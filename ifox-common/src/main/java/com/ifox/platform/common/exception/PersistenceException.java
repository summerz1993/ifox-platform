package com.ifox.platform.common.exception;

public class PersistenceException extends ApplicationException {

    public PersistenceException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
