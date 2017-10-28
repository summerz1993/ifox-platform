package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.MenuPermissionEO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuPermissionRepository extends JpaRepository<MenuPermissionEO, String> {

    @Query("SELECT max(menu.level) FROM MenuPermissionEO menu")
    Integer getBottomLevel();

    @Query(value = "DELETE isrmp FROM ifox_sys_role_menu_permission AS isrmp WHERE isrmp.menu_permission = :menuId", nativeQuery = true)
    void deleteMenuRoleRelation(@Param("menuId") String menuId);

    MenuPermissionEO findFirstByUrlEquals(String url);

    List<MenuPermissionEO> findAllByParentIdEquals(String parentId);

}
