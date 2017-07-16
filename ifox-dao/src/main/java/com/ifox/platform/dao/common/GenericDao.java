package com.ifox.platform.dao.common;


import com.ifox.platform.entity.base.BaseEntity;

import java.io.Serializable;

/**
 * @author Yeager
 *
 * 泛型DAO
 * @param <T>
 * @param <ID>
 */
public interface GenericDao<T extends BaseEntity, ID extends Serializable>
        extends OperationsDao<T, ID> {
}
