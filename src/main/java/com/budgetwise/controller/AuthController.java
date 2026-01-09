package com.budgetwise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.budgetwise.model.User;
import com.budgetwise.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    // TEST
    @GetMapping("/test")
    public String test() {
        return "Auth controller working";
    }

    // SIGNUP
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepo.findByEmail(user.getEmail()) != null) {
            return "Email already exists";
        }
        userRepo.save(user);
        return "Signup successful";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User loginData) {
        User user = userRepo.findByEmail(loginData.getEmail());

        if (user == null) {
            return "User not found";
        }

        if (!user.getPassword().equals(loginData.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }
}
