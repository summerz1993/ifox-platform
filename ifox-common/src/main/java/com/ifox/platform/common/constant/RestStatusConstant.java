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
     * 以下为自定义异常(从480开始排列)
     */
    // 用户名或者密码错误
    public static final Integer USER_NAME_OR_PASSWORD_ERROR = 480;
    //Token校验失败
    public static final Integer TOKEN_ERROR = 481;
    //登录名已经存在
    public static final Integer EXISTED_LOGIN_NAME = 482;
    //无效状态
    public static final Integer INVALID_STATUS = 483;
    //不支持的文件类型
    public static final Integer NOT_SUPPORT_FILE_TYPE = 484;
    //不支持的服务名称
    public static final Integer NOT_SUPPORT_SERVICE_NAME = 485;
    //不允许删除自身账号
    public static final Integer DELETE_SELF_ERROR = 486;
    //菜单包含子菜单，不可直接删除，必须先删除子菜单
    public static final Integer CONTAIN_CHILD_MENU_CAN_NOT_DELETE = 487;
    //系统内置菜单不可删除
    public static final Integer BUILD_IN_SYSTEM_CAN_NOT_DELETE = 488;
}
