package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.RoleEO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEO, String> {

    List<RoleEO> findByIdentifier(String identifier);

    List<RoleEO> findByNameLikeAndStatusEquals(String name, RoleEO.RoleEOStatus status);

    Page<RoleEO> findAllByNameLikeAndStatusEquals(String name, RoleEO.RoleEOStatus status, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM ifox_sys_role_menu_permission WHERE role IN (:roleIdList) AND menu_permission = :menuPermissionId", nativeQuery = true)
    Integer countByRoleIdListAndMenuPermission(@Param("roleIdList") String[] roleIdList, @Param("menuPermissionId") String menuPermissionId);

}
