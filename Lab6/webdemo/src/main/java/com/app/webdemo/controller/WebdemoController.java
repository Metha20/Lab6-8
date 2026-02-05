package com.app.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.app.webdemo.model.RegistrationForm;

import jakarta.validation.Valid;

@Controller
public class WebdemoController {

    // หน้า Home
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // เปิดหน้า Registration
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    // กดปุ่ม Register
    @PostMapping("/register")
    public String handleRegistration(
            @Valid RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model) {

        // ถ้ามี error → กลับหน้าเดิม
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        // ไม่มี error → ส่งข้อมูลไป success.html
        model.addAttribute("firstName", registrationForm.getFirstName());
        model.addAttribute("lastName", registrationForm.getLastName());
        model.addAttribute("country", registrationForm.getCountry());
        model.addAttribute("dob", registrationForm.getDob());
        model.addAttribute("email", registrationForm.getEmail());

        return "success";
    }
}
