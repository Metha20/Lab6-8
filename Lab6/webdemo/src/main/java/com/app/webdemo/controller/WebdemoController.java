package com.app.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.webdemo.model.RegistrationForm;

import jakarta.validation.Valid;

@Controller
public class WebdemoController {

    // =========================
    // หน้า Home
    // =========================
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // =========================
    // หน้า Registration
    // =========================
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    // =========================
    // POST : Register
    // =========================
    @PostMapping("/register")
    public String handleRegistration(
            @Valid RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model) {

        // มี error → กลับหน้า registration
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

    // =========================
    // REST API (Module 3)
    // =========================

    // PUT : แก้ไขข้อมูล (ตัวอย่าง)
    @PutMapping("/users/{id}")
    @ResponseBody
    public String updateUser(@PathVariable int id) {
        return "PUT success: update user id = " + id;
    }

    // DELETE : ลบข้อมูล (ตัวอย่าง)
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable int id) {
        return "DELETE success: delete user id = " + id;
    }
}
