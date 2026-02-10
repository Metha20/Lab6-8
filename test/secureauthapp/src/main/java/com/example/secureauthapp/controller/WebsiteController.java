package com.example.secureauthapp.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.secureauthapp.service.CustomUserDetailsService;

@Controller
public class WebsiteController {

    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public WebsiteController(CustomUserDetailsService userDetailsService,
                             AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    // ===== HOME =====
    @GetMapping("/home")
    public String home(Model model) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());
        return "home";
    }

    // ===== LOGIN =====
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ===== REGISTER =====
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password) {

        try {
            userDetailsService.registerUser(username, password);
        } catch (Exception e) {
            return "redirect:/register?error";
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "redirect:/login?success";
    }

    // ===== GREET =====
    @GetMapping("/greet")
    public String greet(Model model) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());
        return "greet";
    }
}
