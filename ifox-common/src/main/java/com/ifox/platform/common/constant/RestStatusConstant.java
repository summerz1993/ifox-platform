package com.ifox.platform.common.constant;

/**
 * rest接口状态常量
 * @author Yeager
 */
public final class RestStatusConstant {

    /**
     * 标准http状态
     */

    public static final Integer SUCCESS = 200;


    public static final Integer INVALID_REQUEST = 400;

    public static final Integer UNAUTHORIZED = 401;

    public static final Integer NOT_FOUND = 404;

    public static final Integer SERVER_EXCEPTION = 500;

    /**
     * 以下为自定义异常:客户端自定义异常从460开始排列, 服务器端自定义异常从700开始排列
     */
    // 用户名或者密码错误
    public static final Integer USER_NAME_OR_PASSWORD_ERROR = 460;
    // 原始密码错误
    public static final Integer ORIGINAL_PASSWORD_ERROR = 461;
    // 新密码不一致
    public static final Integer NEW_PASSWORD_NOT_EQUAL = 462;



    //Token校验失败
    public static final Integer TOKEN_ERROR = 700;
    //登录名已经存在
    public static final Integer EXISTED_LOGIN_NAME = 701;
    //无效状态
    public static final Integer INVALID_STATUS = 702;
    //不支持的文件类型
    public static final Integer NOT_SUPPORT_FILE_TYPE = 703;
    //不支持的服务名称
    public static final Integer NOT_SUPPORT_SERVICE_NAME = 704;
    //不允许删除自身账号
    public static final Integer DELETE_SELF_ERROR = 705;
    //菜单包含子菜单，不可直接删除，必须先删除子菜单
    public static final Integer CONTAIN_CHILD_MENU_CAN_NOT_DELETE = 706;
    //系统内置不可删除
    public static final Integer BUILD_IN_SYSTEM_CAN_NOT_DELETE = 707;
    //父菜单权限不存在
    public static final Integer PARENT_MENU_PERMISSION_NOT_FOUND = 708;
    //已经存在的identifier
    public static final Integer EXISTED_IDENTIFIER = 709;

}
