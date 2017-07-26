package com.ifox.platform.dao.common.impl;


import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.base.BaseEntity;
import com.ifox.platform.utility.dao.HQLUtil;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Yeager
 *
 * 泛型DAO hibernate实现
 * @param <T> 实体
 * @param <ID> 主键
 */
@Repository("genericHibernateDaoImpl")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericHibernateDaoImpl<T extends BaseEntity, ID extends Serializable> extends HibernateDaoSupport implements GenericDao<T, ID> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Class<?> entityClass;

    /**
     * 初始化HibernateTemplate的SessionFactory
     * @param sessionFactory session工厂
     */
    @Autowired
    public void setSessionFactorySuper(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 构造方法初始化entityClass
     */
    public GenericHibernateDaoImpl() {
        logger.info("初始化GenericHibernateDaoImpl");
        Class<?> c = this.getClass();
        Type t = c.getGenericSuperclass();
        if (t instanceof ParameterizedType) {
            this.entityClass = (Class<?>) ((ParameterizedType) t)
                    .getActualTypeArguments()[0];
        }
    }

    /**
     * 保存方法
     * @param entity 对象
     * @return 主键
     */
    @SuppressWarnings("unchecked")
    @Override
    public ID save(T entity) {
        return (ID) getHibernateTemplate().save(entity);
    }

    /**
     * 通过主键删除实体对象
     * @param id 主键
     */
    @Override
    public void deleteByID(ID id) {
        T entity = get(id);
        getHibernateTemplate().delete(entity);
        getHibernateTemplate().flush();
    }

    /**
     * 通过多个主键删除实体对象
     * @param ids 多个主键
     */
    @Override
    public void deleteMulti(ID[] ids) {
        if (ids == null || ids.length == 0){
            logger.warn("删除多个entity失败, ids为null");
            return;
        }
        for (ID id : ids) {
            T entity = get(id);
            getHibernateTemplate().delete(entity);
        }
        getHibernateTemplate().flush();
    }

    /**
     * 更新实体对象
     * @param entity 实体对象
     */
    @Override
    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 通过ID获取实体对象
     * @param id 主键
     * @return 实体对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(ID id) {
        return (T)getHibernateTemplate().get(entityClass, id);
    }

    /**
     * 查询所有数据
     * @return 所有实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listAll() {
        return getHibernateTemplate().execute(session -> (List<T>) HQLUtil.createQueryByHQL(session, HQLUtil.generateFromEntityHQL(entityClass.getName()), (Object[]) null).list());
    }

    /**
     * 条件查询实体对象集合
     * @param queryPropertyList 查询条件
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listByQueryProperty(List<QueryProperty> queryPropertyList) {
        return getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryPropertyList), (Object[]) null).list());
    }

    /**
     * 条件查询实体对象集合
     * @param queryConditions 查询条件
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listByQueryConditions(QueryConditions queryConditions) {
        return getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryConditions), (Object[]) null).list());
    }

    /**
     * 查询最顶部集合
     * @param rows 行数
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listTopRows(int rows) {
        return getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFromEntityHQL(entityClass.getName()), (Object[]) null).setFirstResult(0).setMaxResults(rows > MAX_ROWS ? MAX_ROWS : rows).list());
    }

    /**
     * 条件查询最顶部集合
     * @param rows 行数
     * @param queryPropertyList 查询条件
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listTopRowsByQueryProperty(int rows, List<QueryProperty> queryPropertyList) {
        return getHibernateTemplate().execute(session ->
                (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryPropertyList), (Object[]) null)
                .setFirstResult(0)
                .setMaxResults(rows > MAX_ROWS ? MAX_ROWS : rows)
                .list());
    }

    /**
     * 条件查询最顶部集合
     * @param rows 行数
     * @param queryConditions 查询条件
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listTopRowsByQueryConditions(int rows, QueryConditions queryConditions) {
        return getHibernateTemplate().execute(session ->
                (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryConditions), (Object[]) null)
                .setFirstResult(0)
                .setMaxResults(rows > MAX_ROWS ? MAX_ROWS : rows)
                .list());
    }

    /**
     * 数据总数
     * @return 总数
     */
    @Override
    public int countAll() {
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, HQLUtil.generateCountEntityHQL(entityClass.getName())).uniqueResult();
            return result == null ? 0 : Integer.valueOf(result.toString());
        });
    }

    /**
     * 条件查询数据总数
     * @param queryPropertyList 查询条件
     * @return 总数
     */
    @Override
    public int countByQueryProperty(List<QueryProperty> queryPropertyList) {
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, HQLUtil.generateCountEntityHQL(entityClass.getName(), queryPropertyList)).uniqueResult();
            return result == null ? 0 : Integer.valueOf(result.toString());
        });
    }

    /**
     * 条件查询数据总数
     * @param queryConditions 查询条件
     * @return 总数
     */
    @Override
    public int countByQueryConditions(QueryConditions queryConditions) {
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, HQLUtil.generateCountEntityHQL(entityClass.getName(), queryConditions)).uniqueResult();
            return result == null ? 0 : Integer.valueOf(result.toString());
        });
    }

    /**
     * 分页条件查询
     * @param simplePage 分页数据
     * @param queryPropertyList 查询条件
     * @return 分页实体
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> pageByQueryProperty(SimplePage simplePage, List<QueryProperty> queryPropertyList) {
        Page<T> page = new Page<>(simplePage);
        List<T> content = getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryPropertyList), (Object[]) null)
                .setFirstResult(simplePage.getFirstResult())
                .setMaxResults(simplePage.getPageSize())
                .list());
        int totalCount = countByQueryProperty(queryPropertyList);

        page.setContent(content);
        page.setTotalCount(totalCount);
        return page;
    }


    /**
     * 分页条件查询
     * @param simplePage 分页数据
     * @param queryConditions 查询条件
     * @return 分页实体
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> pageByQueryConditions(SimplePage simplePage, QueryConditions queryConditions) {
        Page<T> page = new Page<>(simplePage);
        List<T> content = getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, HQLUtil.generateFinalHQL(entityClass.getName(), queryConditions), (Object[]) null)
                .setFirstResult(simplePage.getFirstResult())
                .setMaxResults(simplePage.getPageSize())
                .list());
        int totalCount = countByQueryProperty(queryConditions.getQueryPropertyList());

        page.setContent(content);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * 删除entity
     * @param entity 实体对象
     */
    @Override
    public void deleteByEntity(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 只用于自定义复杂HQL查询 - 慎用
     * @param hql HQL语句
     * @param values 参数值
     * @return 实体集合
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listByCustomizedHQL(String hql, Object... values) {
        return getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, hql, values).list());
    }

    /**
     * 只用于自定义复杂HQL查询 - 慎用
     * @param rows 行数
     * @param hql HQL语句
     * @param values 参数值
     * @return 查询结果
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> listTopRowsByCustomizedHQL(int rows, String hql, Object... values) {
        return getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, hql, values)
                .setFirstResult(0)
                .setMaxResults(rows > MAX_ROWS ? MAX_ROWS : rows)
                .list());
    }

    /**
     * 自定义复杂HQL分页查询
     * @param simplePage 分页数据
     * @param hql HQL语句
     * @param values 参数值
     * @return 分页对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> pageByCustomizedHQL(SimplePage simplePage, String hql, Object... values) {
        Page<T> page = new Page<>(simplePage);
        List<T> content = getHibernateTemplate().execute(session -> (List<T>)HQLUtil.createQueryByHQL(session, hql, values)
                .setFirstResult(simplePage.getFirstResult())
                .setMaxResults(simplePage.getPageSize())
                .list());
        page.setContent(content);
        return page;
    }

    /**
     * 自定义复杂HQL数量查询
     * @param hql HQL语句
     * @param values 参数值
     * @return 数量
     */
    @Override
    public int countByCustomizedHQL(String hql, Object... values) {
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, hql, values).uniqueResult();
            return result == null ? 0 : Integer.valueOf(result.toString());
        });
    }

    /**
     * 从session中移除entity管理
     * @param entity 实体对象
     */
    @Override
    public void evictEntity(T entity) {
        getHibernateTemplate().evict(entity);
    }

    /**
     * 合并实体对象变更到数据库
     * @param entity 实体对象
     * @return 实体对象
     */
    @Override
    public T merge(T entity) {
        return getHibernateTemplate().merge(entity);
    }

}
