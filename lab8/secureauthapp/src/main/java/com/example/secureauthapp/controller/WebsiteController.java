package com.example.secureauthapp.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

    // ================= HOME =================
    @GetMapping("/home")
    public String homepage(Model model) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // ถ้ายังไม่ login
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        // ส่ง username ไปหน้า HTML
        String username = authentication.getName();
        model.addAttribute("username", username);

        // ดึง role
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_STAFF");

        // แยกหน้า admin / viewer
        if (role.equals("ROLE_ADMIN")) {
            return "admin";
        } else {
            return "viewer";
        }
    }

    // ================= LOGIN =================
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ================= REGISTER =================
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // ================= REGISTER POST =================
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {

        try {
            userDetailsService.registerUser(username, password, role);
        } catch (Exception e) {
            return "redirect:/register?error";
        }

        // Login อัตโนมัติหลังสมัคร
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ไปหน้า home เพื่อเช็ค role
        return "redirect:/home";
    }
}
