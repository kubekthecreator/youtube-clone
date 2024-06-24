package com.kubekthecreator.projects.youtubeclone.controller;

import com.kubekthecreator.projects.youtubeclone.service.UserRegistrationService;
import com.kubekthecreator.projects.youtubeclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRegistrationService userRegistrationService;
    private final UserService userService;

    @GetMapping("/register")
    public Mono<ResponseEntity<String>> register(Authentication authentication) {
        return Mono.justOrEmpty(authentication.getPrincipal())
                .cast(Jwt.class)
                .flatMap(jwt -> userRegistrationService.registerUser(jwt.getTokenValue()))
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error occurred during user registration")));
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
