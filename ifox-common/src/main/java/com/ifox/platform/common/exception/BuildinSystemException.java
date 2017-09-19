package com.ifox.platform.common.exception;

/**
 * 系统内置属性异常
 */
public class BuildinSystemException extends ApplicationException {
    public BuildinSystemException(Integer expStatus, String message) {
        super(expStatus, message);
    }
}
