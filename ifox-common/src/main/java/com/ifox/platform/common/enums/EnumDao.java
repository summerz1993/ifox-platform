package com.ifox.platform.common.enums;

/**
 * @author Yeager
 *
 * DAO层枚举值
 */
public class EnumDao {

    /**
     * 排序方式
     */
    public enum OrderMode{
        //顺序
        ASC,
        //倒序
        DESC
    }

    /**
     * 操作类型
     */
    public enum Operation{
        /** 等于 */
        EQUAL,

        /** 不等于 */
        NOT_EQUAL,

        /** 大于 */
        GREATER_THAN,

        /** 小于 */
        LESS_THAN,

        /** 大于等于 */
        GREATER_EQUAL,

        /** 小于等于 */
        LESS_EQUAL,

        /** 相似 */
        LIKE,

        /** 包含 */
        IN,

        /** 为Null */
        IS_NULL,

        /** 不为Null */
        IS_NOT_NULL,

    }

}
