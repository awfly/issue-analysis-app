package com.amgchv.services;

import com.amgchv.models.Role;
import com.amgchv.repositories.RoleJpaRepository;
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
