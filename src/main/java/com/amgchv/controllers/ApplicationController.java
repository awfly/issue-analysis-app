package com.amgchv.controllers;

import com.amgchv.models.User;
import com.amgchv.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login/index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup/index";
    }

    @PostMapping("/signup")
    public String signup(User user, @RequestParam("password") String password, HttpServletRequest request) {
        userService.register(user, "guest");
        authenticateUserAndSetSession(user, password, request);
        return "redirect:/";
    }

    private void authenticateUserAndSetSession(User user, String password, HttpServletRequest request) {
        String username = user.getAccount();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
