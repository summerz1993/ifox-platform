package com.ifox.platform.dao.sys.impl;

import com.ifox.platform.dao.common.impl.GenericHibernateDaoImpl;
import com.ifox.platform.dao.sys.RoleDao;
import com.ifox.platform.entity.sys.RoleEO;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RoleDaoImpl extends GenericHibernateDaoImpl<RoleEO, String> implements RoleDao {

    /**
     * 根据角色ID和菜单权限ID计算对应数量
     * @param roleIdList
     * @param menuPermissionId
     * @return 数量
     */
    @Transactional
    @Override
    public Integer countByRoleIdListAndMenuPermission(String[] roleIdList, String menuPermissionId) {
//        StringBuilder roleIdSQL = new StringBuilder();
//        for (int i = 0; i < roleIdList.length; i ++) {
//            roleIdSQL.append("'").append(roleIdList[i]).append("'");
//            if (i != (roleIdList.length - 1)) roleIdSQL.append(",");
//        }
        String sql = "SELECT COUNT(*) FROM ifox_sys_role_menu_permission WHERE role IN (:roleIdList) AND menu_permission = :menuPermissionId";
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Object result = session.createSQLQuery(sql).setParameter("menuPermissionId", menuPermissionId).setParameter("roleIdList", roleIdList).uniqueResult();
        return result == null ? null : Integer.valueOf(result.toString());
    }
}
