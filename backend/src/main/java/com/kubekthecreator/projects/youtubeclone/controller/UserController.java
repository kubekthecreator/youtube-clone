package com.kubekthecreator.projects.youtubeclone.controller;

import com.kubekthecreator.projects.youtubeclone.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;

    @GetMapping("/register")
    public ResponseEntity<String> register(Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userRegistrationService.registerUser(jwt.getTokenValue());
            return ResponseEntity.ok(authentication.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred during user registration");
        }
    }
}
