package com.amgchv.services;

import com.amgchv.models.User;
import java.util.List;

public interface UserService {

    void register(User user, String roleName);

    List<User> findAll();

    void deleteById(String id);

    User getByUserName(String userName);

}
