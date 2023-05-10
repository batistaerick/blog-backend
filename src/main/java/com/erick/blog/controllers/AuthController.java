package com.erick.blog.controllers;

import com.erick.blog.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService service;

    @PostMapping
    public ResponseEntity<String> token(Authentication authentication) {
        String token = service.generateToken(authentication);
        return ResponseEntity.ok(token);
    }

}
