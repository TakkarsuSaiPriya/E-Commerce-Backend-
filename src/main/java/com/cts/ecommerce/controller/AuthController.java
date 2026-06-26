package com.cts.ecommerce.controller;

import com.cts.ecommerce.entity.User;
import com.cts.ecommerce.repository.UserRepository;
import com.cts.ecommerce.service.AuthService;
import com.cts.ecommerce.service.AuditService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user,
                                      HttpServletRequest request) {
        try {
            User saved = authService.register(user);
            auditService.log(
                    user.getUsername(), "POST",
                    request.getRequestURI(),
                    user, saved.getId(), "SUCCESS"
            );
            Map<String, Object> response = new HashMap<>();
            response.put("id", saved.getId());
            response.put("username", saved.getUsername());
            response.put("role", saved.getRole());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            auditService.log(
                    user.getUsername(), "POST",
                    request.getRequestURI(),
                    user, e.getMessage(), "FAILED"
            );
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user,
                                   HttpServletRequest request) {
        try {
            String token = authService.login(user);

            String role = userRepository.findByUsername(user.getUsername())
                    .map(User::getRole)
                    .orElse("USER");

            auditService.log(
                    user.getUsername(), "POST",
                    request.getRequestURI(),
                    user, "Login Success", "SUCCESS"
            );

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("role", role);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            auditService.log(
                    user.getUsername(), "POST",
                    request.getRequestURI(),
                    user, e.getMessage(), "FAILED"
            );
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}