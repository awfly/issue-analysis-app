package com.amgchv.services.impl;

import com.amgchv.exceptions.PasswordPreviousException;
import com.amgchv.exceptions.UserAlreadyExistsException;
import com.amgchv.exceptions.WrongPasswordException;
import com.amgchv.models.Role;
import com.amgchv.models.User;
import com.amgchv.repositories.RoleJpaRepository;
import com.amgchv.repositories.UserJpaRepository;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        user.setRole(role);
        userJpaRepository.save(user);
    }

    public List<User> findAll() {
        return userJpaRepository.findAll();
    }

    public void deleteById(String id) {
        userJpaRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public User getByUserName(String userName) {
        return userJpaRepository.findByAccount(userName);
    }

    @Override
    public User getById(Long id) {
        return userJpaRepository.findById(id).get();
    }

    @Override
    public void updateUserRole(User currentUser, String newRole) {
        Role role = roleJpaRepository.findById(Long.valueOf(newRole)).get();
        currentUser.setRole(role);
        userJpaRepository.save(currentUser);
    }

    @Override
    public void updateUsername(User currentUser, String account, String password) {
        if (userJpaRepository.findByAccount(account) != null)
            throw new UserAlreadyExistsException("User with name" + account + "already exists");
        if (!StringUtils.isEmpty(account)) {
            currentUser.setAccount(account);
            userJpaRepository.save(currentUser);
        }
    }

    @Override
    public void updateEmail(User currentUser, String password, String email) {
        if (!passwordEncoder.matches(password, currentUser.getPassword()))
            throw new WrongPasswordException("Passwords do not matches");
        if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(email) && userJpaRepository.findByEmail(email) == null) {
            currentUser.setEmail(email);
            userJpaRepository.save(currentUser);
        }
    }

    @Override
    public void updatePassword(User currentUser, String oldPassword, String newPassword) {
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword()))
            throw new WrongPasswordException("Passwords do not matches");
        if (passwordEncoder.matches(newPassword, currentUser.getPassword()))
            throw new PasswordPreviousException("Password must not be the same as previous");
        if (!StringUtils.isEmpty(newPassword)) {
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userJpaRepository.save(currentUser);
        } else throw new WrongPasswordException("Password is empty");
    }
}
