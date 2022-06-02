package com.epam.esm.repository;

import com.epam.esm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RoleRepository interface implements JpaRepository functionality for the Role entity
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
