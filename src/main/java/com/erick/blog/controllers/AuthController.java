package com.erick.blog.controllers;

import com.erick.blog.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final TokenService service;

    @PostMapping
    public ResponseEntity<String> token(Authentication authentication) {
        LOG.debug("Token Requested for user: '{}'", authentication.getName());
        String token = service.generateToken(authentication);
        LOG.debug("Token granted {}", token);
        return ResponseEntity.ok(token);
    }

}
