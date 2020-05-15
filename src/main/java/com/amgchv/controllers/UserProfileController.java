package com.amgchv.controllers;

import com.amgchv.models.User;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "users/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @GetMapping(value = "/{name}")
    public String userProfile(@PathVariable String name, Model model) {
        User user = userService.getByUserName(name);
        model.addAttribute("user", user);
        return "profile/userProfile";
    }
}
