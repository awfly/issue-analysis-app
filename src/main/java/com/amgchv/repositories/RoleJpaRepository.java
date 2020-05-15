package com.amgchv.repositories;

import com.amgchv.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
