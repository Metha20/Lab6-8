package com.example.mvcwebdemoas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcwebdemoController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Let's get started");
        return "index";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
}