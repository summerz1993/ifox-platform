package com.ifox.platform.baseservice;

import java.io.Serializable;

/**
 * service泛型接口
 * @param <T> bean
 * @param <ID> 主键
 */
public interface GenericService<T, ID extends Serializable> extends OperationsService<T, ID>{
}
