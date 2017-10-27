package com.ifox.platform.dao.common;


import com.ifox.platform.common.bean.OperationsCommon;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.entity.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Yeager
 *
 * 定义DAO层操作方法
 * @param <T> 实体
 * @param <ID> 主键
 */
public interface OperationsDao<T extends BaseEntity, ID extends Serializable> extends OperationsCommon<T, ID> {

    /**
     * 只用于自定义复杂HQL查询 - 慎用
     * @param hql HQL语句
     * @param values 参数值
     * @return 实体集合
     */
    List<T> listByCustomizedHQL(String hql, Object... values);

    /**
     * 只用于自定义复杂HQL查询 - 慎用
     * @param rows 行数
     * @param hql HQL语句
     * @param values 参数值
     * @return 查询结果
     */
    List<T> listTopRowsByCustomizedHQL(int rows, String hql, Object... values);

    /**
     * 自定义复杂HQL分页查询
     * @param simplePage 分页数据
     * @param hql HQL语句
     * @param values 参数值
     * @return 分页对象
     */
    Page<T> pageByCustomizedHQL(SimplePage simplePage, String hql, Object... values);

    /**
     * 自定义复杂HQL数量查询
     * @param hql HQL语句
     * @param values 参数值
     * @return 数量
     */
    int countByCustomizedHQL(String hql, Object... values);

    /**
     * 从session中移除entity管理
     * @param entity 实体对象
     */
    void evictEntity(T entity);

    /**
     * 合并实体对象变更到数据库
     * @param entity 实体对象
     * @return 实体对象
     */
    T merge(T entity);

}
