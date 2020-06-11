package com.amgchv.controllers;

import com.amgchv.models.Role;
import com.amgchv.models.User;
import com.amgchv.services.RoleService;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users/")
    public String list(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userAdmin/list";
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable String id,
                         RedirectAttributes redirectAttributes,
                         @RequestHeader(required = false) String referer) {
        userService.deleteById(id);

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().forEach(redirectAttributes::addAttribute);

        return "redirect:" + components.getPath();
    }

    @GetMapping("/users/changeRole/{id}")
    public String changeRole(@PathVariable String id, Model model) {
        User user = userService.getById(Long.valueOf(id));
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "userAdmin/changeRole";
    }

    @PostMapping("/users/changeRole/{id}")
    public String saveChangedRole(@PathVariable String id, @RequestParam String role) {
        User user = userService.getById(Long.valueOf(id));
        userService.updateUserRole(user, role);
        return "redirect:/users/";
    }


    @GetMapping("/settings/")
    public String settings() {
        return "settings/settings";
    }

}
