package com.amgchv.repositories;

import com.amgchv.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByAccount(String account);
}
