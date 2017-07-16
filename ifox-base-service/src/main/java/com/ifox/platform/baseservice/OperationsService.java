package com.ifox.platform.baseservice;

import com.ifox.platform.common.bean.OperationsCommon;

import java.io.Serializable;

/**
 * service层操作接口
 * @param <T> bean
 * @param <ID> 主键
 */
public interface OperationsService<T, ID extends Serializable> extends OperationsCommon<T, ID> {
}
