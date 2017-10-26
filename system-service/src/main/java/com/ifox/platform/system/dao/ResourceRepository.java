package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.ResourceEO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<ResourceEO, String> {
}
