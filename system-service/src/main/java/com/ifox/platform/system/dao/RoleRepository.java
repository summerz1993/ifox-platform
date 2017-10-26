package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.RoleEO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEO, String> {

    List<RoleEO> findByIdentifier(String identifier);

    List<RoleEO> findByNameLikeAndStatusEquals(String name, RoleEO.RoleEOStatus status);

    Page<RoleEO> findAllByNameLikeAndStatusEquals(String name, RoleEO.RoleEOStatus status, Pageable);

}
