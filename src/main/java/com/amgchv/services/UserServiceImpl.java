package com.amgchv.services;

import com.amgchv.models.Role;
import com.amgchv.models.User;
import com.amgchv.repositories.RoleJpaRepository;
import com.amgchv.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleJpaRepository roleJpaRepository;

    @Transactional
    public void register(User user, String roleName) {
        Role role = roleJpaRepository.findByName(roleName);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        userJpaRepository.save(user);
    }

    @PreAuthorize("hasAuthority('manageUser')")
    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    @PreAuthorize("hasAuthority('manageUser')")
    public void deleteById(String id) {
        userJpaRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public User getByUserName(String userName) {
        return userJpaRepository.findByAccount(userName);
    }
}
