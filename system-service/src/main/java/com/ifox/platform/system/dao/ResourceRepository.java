package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.ResourceEO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEO, String> {

    Page<ResourceEO> findAllByNameLikeAndTypeEquals(String name, ResourceEO.ResourceEOType type, Pageable pageable);

    ResourceEO findFirstByControllerEquals(String controller);

}
