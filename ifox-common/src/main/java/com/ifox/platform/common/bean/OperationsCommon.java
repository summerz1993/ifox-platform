package com.ifox.platform.common.bean;


import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;

import java.util.List;

/**
 * @author Yeager
 *
 * 定义通用操作接口
 *
 * @param <T> 对象
 * @param <ID> 主键 - 可拓展单主键和联合主键
 */
public interface OperationsCommon<T, ID> {

    /**
     * 返回的最大行数
     */
    int MAX_ROWS = 10000;

    /**
     * 保存方法
     * @param bean 对象
     * @return 主键
     */
    ID save(T bean);

    /**
     * 通过主键删除实体对象
     * @param id 主键
     */
    void deleteByID(ID id);

    /**
     * 通过多个主键删除实体对象
     * @param ids 多个主键
     */
    void deleteMulti(ID... ids);

    /**
     * 更新实体对象
     * @param bean 实体对象
     */
    void update(T bean);

    /**
     * 通过ID获取实体对象
     * @param id 主键
     * @return 实体对象
     */
    T get(ID id);

    /**
     * 查询所有数据
     * @return 所有实体对象集合
     */
    List<T> listAll();

    /**
     * 条件查询实体对象集合
     * @param queryPropertyList 查询条件
     * @return 实体对象集合
     */
    List<T> listByQueryProperty(List<QueryProperty> queryPropertyList);

    /**
     * 条件查询实体对象集合
     * @param queryConditions 查询条件
     * @return 实体对象集合
     */
    List<T> listByQueryConditions(QueryConditions queryConditions);

    /**
     * 查询最顶部集合
     * @param rows 行数
     * @return 实体对象集合
     */
    List<T> listTopRows(int rows);

    /**
     * 条件查询最顶部集合
     * @param rows 行数
     * @param queryPropertyList 查询条件
     * @return 实体对象集合
     */
    List<T> listTopRowsByQueryProperty(int rows, List<QueryProperty> queryPropertyList);

    /**
     * 条件查询最顶部集合
     * @param rows 行数
     * @param queryConditions 查询条件
     * @return 实体对象集合
     */
    List<T> listTopRowsByQueryConditions(int rows, QueryConditions queryConditions);

    /**
     * 数据总数
     * @return 总数
     */
    int countAll();

    /**
     * 条件查询数据总数
     * @param queryPropertyList 查询条件
     * @return 总数
     */
    int countByQueryProperty(List<QueryProperty> queryPropertyList);

    /**
     * 条件查询数据总数
     * @param queryConditions 查询条件
     * @return 总数
     */
    int countByQueryConditions(QueryConditions queryConditions);

    /**
     * 分页条件查询
     * @param simplePage 分页数据
     * @param queryPropertyList 查询条件
     * @return 分页实体
     */
    Page<T> pageByQueryProperty(SimplePage simplePage, List<QueryProperty> queryPropertyList);

    /**
     * 分页条件查询
     * @param simplePage 分页数据
     * @param queryConditions 查询条件
     * @return 分页实体
     */
    Page<T> pageByQueryConditions(SimplePage simplePage, QueryConditions queryConditions);

}
