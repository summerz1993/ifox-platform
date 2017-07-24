package com.ifox.platform.baseservice.impl;

import com.ifox.platform.baseservice.GenericService;
import com.ifox.platform.common.bean.QueryConditions;
import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.page.Page;
import com.ifox.platform.common.page.SimplePage;
import com.ifox.platform.dao.common.GenericDao;
import com.ifox.platform.entity.base.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author Yeager
 *
 * 泛型service层实现
 * Transactional注解默认是transactionManager bean
 */
@Transactional
public class GenericServiceImpl<T extends BaseEntity, ID extends Serializable> implements GenericService<T, ID> {


//    @Resource(name = "genericHibernateDaoImpl")
    protected GenericDao<T, ID> genericDao;

    @Override
    public ID save(T bean) {
        return genericDao.save(bean);
    }

    @Override
    public void deleteByID(ID id) {
        genericDao.deleteByID(id);
    }

    @Override
    public void deleteMulti(ID[] ids) {
        genericDao.deleteMulti(ids);
    }

    @Override
    public void update(T bean) {
        genericDao.update(bean);
    }

    @Override
    public T get(ID id) {
        return genericDao.get(id);
    }

    @Override
    public List<T> listAll() {
        return genericDao.listAll();
    }

    @Override
    public List<T> listByQueryProperty(QueryProperty[] queryProperties) {
        return genericDao.listByQueryProperty(queryProperties);
    }

    @Override
    public List<T> listByQueryConditions(QueryConditions queryConditions) {
        return genericDao.listByQueryConditions(queryConditions);
    }

    @Override
    public List<T> listTopRows(int rows) {
        return genericDao.listTopRows(rows);
    }

    @Override
    public List<T> listTopRowsByQueryProperty(int rows, QueryProperty[] queryProperties) {
        return genericDao.listTopRowsByQueryProperty(rows, queryProperties);
    }

    @Override
    public List<T> listTopRowsByQueryConditions(int rows, QueryConditions queryConditions) {
        return genericDao.listTopRowsByQueryConditions(rows, queryConditions);
    }

    @Override
    public int countAll() {
        return genericDao.countAll();
    }

    @Override
    public int countByQueryProperty(QueryProperty[] queryProperties) {
        return genericDao.countByQueryProperty(queryProperties);
    }

    @Override
    public int countByQueryConditions(QueryConditions queryConditions) {
        return genericDao.countByQueryConditions(queryConditions);
    }

    @Override
    public Page<T> pageByQueryProperty(SimplePage simplePage, QueryProperty[] queryProperties) {
        return genericDao.pageByQueryProperty(simplePage, queryProperties);
    }

    @Override
    public Page<T> pageByQueryConditions(SimplePage simplePage, QueryConditions queryConditions) {
        return genericDao.pageByQueryConditions(simplePage, queryConditions);
    }
}
