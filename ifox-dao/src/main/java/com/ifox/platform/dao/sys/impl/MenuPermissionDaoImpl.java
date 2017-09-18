package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.common.bean.QueryProperty;
import com.ifox.platform.common.enums.EnumDao;
import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.MenuPermissionDao;
import com.ifox.platform.entity.sys.MenuPermissionEO;
import com.ifox.platform.utility.dao.HQLUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MenuPermissionDaoImpl extends GenericHibernateDaoImpl<MenuPermissionEO, String> implements MenuPermissionDao{

    /**
     * 获取菜单的最大层级
     * @return 最大层级
     */
    @Override
    public Integer getBottomLevel() {
        String hql = "SELECT max(menu.level) FROM MenuPermissionEO menu";
        return getHibernateTemplate().execute(session -> {
            Object result = HQLUtil.createQueryByHQL(session, hql, null).uniqueResult();
            return result == null ? null : Integer.valueOf(result.toString());
        });
    }

    /**
     * 删除菜单权限和角色的关联关系
     * @param menuId menuId
     */
    @Override
    public void deleteMenuRoleRelation(String menuId) {
        String sql = "DELETE isrmp FROM ifox_sys_role_menu_permission AS isrmp WHERE isrmp.menu_permission = :menuId";
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql).setParameter("menuId", menuId).executeUpdate();
    }

    /**
     * 通过URL获取菜单权限实体
     * @param URL URL
     * @return 菜单权限实体
     */
    @Override
    public MenuPermissionEO getByURL(String URL) {
        List<QueryProperty> queryPropertyList = new ArrayList<>();
        queryPropertyList.add(new QueryProperty("url", EnumDao.Operation.EQUAL, URL));
        List<MenuPermissionEO> menuPermissionEOList = listByQueryProperty(queryPropertyList);
        if (!CollectionUtils.isEmpty(menuPermissionEOList)) {
            return menuPermissionEOList.get(0);
        }
        return null;
    }

}
