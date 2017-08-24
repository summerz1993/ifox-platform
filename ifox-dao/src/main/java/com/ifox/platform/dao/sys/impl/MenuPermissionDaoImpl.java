package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.MenuPermissionDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.dao.HQLUtil;
import org.springframework.stereotype.Repository;

@Repository
public class MenuPermissionDaoImpl extends GenericHibernateDaoImpl<MenuPermissionEO, String> implements MenuPermissionDao{

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    @Override
    public Integer getMaxLevel() {
        String hql = "SELECT max(menu.level) FROM MenuPermissionEO menu";
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, hql, null).uniqueResult();
            return result == null ? null : Integer.valueOf(result.toString());
        });
    }
}
