package com.ifox.platform.common.exception;

/**
 * @author Yeager
 *
 * 构造方法异常 - 非强制性
 */
public class ConstructorException extends RuntimeException{

    public ConstructorException() {
        super("构造方法异常");
    }

    public ConstructorException(String message) {
        super(message);
    }
}
