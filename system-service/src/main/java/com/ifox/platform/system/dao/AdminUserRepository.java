package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.AdminUserEO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminUserRepository extends JpaRepository<AdminUserEO, String> {

    AdminUserEO findFirstByLoginNameEquals(String loginName);

    Page<AdminUserEO> findAllByLoginNameLikeAndStatusEqualsAndBuildinSystemEquals(String loginName, AdminUserEO.AdminUserEOStatus status, Boolean buildinSystem, Pageable pageable);

    List<AdminUserEO> findAllByLoginNameLikeAndStatusEqualsAndBuildinSystemEquals(String loginName, AdminUserEO.AdminUserEOStatus status, Boolean buildinSystem);

    @Modifying
    @Query("update AdminUserEO u set u.password = :password where u.id = :id")
    void updatePassword(@Param("password") String password, @Param("id") String id);

}
