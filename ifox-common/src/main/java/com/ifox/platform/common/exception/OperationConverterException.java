package com.ifox.platform.common.exception;

/**
 * @author Yeager
 *
 * DAO层枚举操作符转换异常
 */
public class OperationConverterException extends RuntimeException{

    public OperationConverterException() {
        super("DAO层枚举操作符转换异常, EnumDao.Operation不能为空");
    }

}
