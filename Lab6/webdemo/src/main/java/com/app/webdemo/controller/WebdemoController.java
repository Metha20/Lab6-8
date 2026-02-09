package com.app.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.app.webdemo.model.RegistrationForm;
import com.app.webdemo.model.User;
import com.app.webdemo.repository.UserRepository;

import jakarta.validation.Valid;




@Controller
public class WebdemoController {

    private final UserRepository userRepository;

    public WebdemoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // หน้า Home
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // หน้า Registration
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    // POST : Register + บันทึก DB
    @PostMapping("/register")
    public String handleRegistration(
            @Valid RegistrationForm registrationForm,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        // 👉 Form → Entity
        User user = new User();
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setCountry(registrationForm.getCountry());
        user.setDob(registrationForm.getDob());
        user.setEmail(registrationForm.getEmail());

        // 👉 บันทึก DB
        userRepository.save(user);

        // ส่งข้อมูลไปหน้า success
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("country", user.getCountry());
        model.addAttribute("dob", user.getDob());
        model.addAttribute("email", user.getEmail());

        return "success";
    }

    // PUT
    @PutMapping("/users/{id}")
    @ResponseBody
    public String updateUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setCountry("Updated");
                    userRepository.save(user);
                    return "PUT success: update user id = " + id;
                })
                .orElse("User not found id = " + id);
    }

    // DELETE
    @DeleteMapping("/users/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "DELETE success: delete user id = " + id;
        }
        return "User not found id = " + id;
    }
}
