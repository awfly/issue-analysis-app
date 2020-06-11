package com.amgchv.services.impl;

import com.amgchv.models.Role;
import com.amgchv.repositories.RoleJpaRepository;
import com.amgchv.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleJpaRepository.findAll();
    }
}
