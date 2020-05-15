package com.amgchv.controllers;

import com.amgchv.models.User;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

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

}
