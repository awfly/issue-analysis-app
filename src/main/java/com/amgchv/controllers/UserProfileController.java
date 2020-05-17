package com.amgchv.controllers;

import com.amgchv.models.User;
import com.amgchv.security.UserPrincipal;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping("/editUsername")
    public String updateUsername(@RequestParam String account,
                                 @AuthenticationPrincipal UserPrincipal currentUser,
                                 @RequestParam String password) {
        User user = currentUser.getUser();
        userService.updateUsername(user, account, password);

        return "redirect:/users/user/" + user.getAccount();
    }

    @PostMapping("/editEmail")
    public String updateEmail(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String password,
            @RequestParam String email) {

        User user = currentUser.getUser();
        userService.updateEmail(user, password, email);

        return "redirect:/users/user/" + user.getAccount();
    }

    @PostMapping("/editPassword")
    public String updatePassword(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        User user = currentUser.getUser();
        userService.updatePassword(user, oldPassword, newPassword);

        return "redirect:/users/user/" + user.getAccount();
    }
}
