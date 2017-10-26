package com.ifox.platform.system.dao;

import com.ifox.platform.system.entity.AdminUserEO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUserEO, String> {
}
