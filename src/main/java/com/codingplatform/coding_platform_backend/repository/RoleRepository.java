package com.codingplatform.coding_platform_backend.repository;

import com.codingplatform.coding_platform_backend.models.Role;
import com.codingplatform.coding_platform_backend.models.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
