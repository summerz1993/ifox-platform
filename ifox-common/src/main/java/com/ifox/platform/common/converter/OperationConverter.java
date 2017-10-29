package com.ifox.platform.common.converter;


import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.common.exception.OperationConverterException;

/**
 * @author Yeager
 *
 * DAO层枚举操作符转换器
 */
public class OperationConverter {

    /**
     * DAO层枚举操作符转换方法
     * @param operation 枚举操作
     * @return 对应数据库操作符
     */
    public static String convertOperation(EnumDao.Operation operation){
        if (operation == EnumDao.Operation.EQUAL){
            return "=";
        } else if (operation == EnumDao.Operation.NOT_EQUAL) {
            return "!=";
        } else if (operation == EnumDao.Operation.GREATER_THAN) {
            return ">";
        } else if (operation == EnumDao.Operation.LESS_THAN) {
            return "<";
        } else if (operation == EnumDao.Operation.GREATER_EQUAL) {
            return ">=";
        } else if (operation == EnumDao.Operation.LESS_EQUAL) {
            return "<=";
        } else if (operation == EnumDao.Operation.LIKE) {
            return "LIKE";
        } else if (operation == EnumDao.Operation.IN) {
            return "IN";
        } else if (operation == EnumDao.Operation.IS_NULL) {
            return "IS NULL";
        } else if (operation == EnumDao.Operation.IS_NOT_NULL) {
            return "IS NOT NULL";
        } else {
            throw new OperationConverterException();
        }
    }

}
