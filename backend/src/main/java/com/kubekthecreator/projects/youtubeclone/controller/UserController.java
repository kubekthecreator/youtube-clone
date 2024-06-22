package com.kubekthecreator.projects.youtubeclone.controller;

import com.kubekthecreator.projects.youtubeclone.model.User;
import com.kubekthecreator.projects.youtubeclone.service.UserRegistrationService;
import com.kubekthecreator.projects.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            userRegistrationService.registerUser(jwt.getTokenValue());
            return ResponseEntity.ok(authentication.getName());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred during user registration");
        }
    }

    @PostMapping("/subscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean subscribeUser(@PathVariable String userId) {
        userService.subscribeUser(userId);
        return true;
    }

    @PostMapping("/unsubscribe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean unSubscribeUser(@PathVariable String userId) {
        userService.unSubscribeUser(userId);
        return true;
    }

    @GetMapping("/{userId}/history")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> userHistory(@PathVariable String userId) {
        return userService.userHistory(userId);
    }
}
