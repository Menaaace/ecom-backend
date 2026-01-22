package com.example.ecommerce.controller;

import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        return authService.login(request.get("username"), request.get("password"));
    }
    @PostMapping("/register")
    public User register(@RequestBody Map<String, String> request) {
        return authService.register(request.get("username"), request.get("password"));
    }
}