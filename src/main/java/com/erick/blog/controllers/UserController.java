package com.erick.blog.controllers;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find-by-id/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-by-email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<User> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @GetMapping("/validate-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String email, @RequestParam String password) {
        Boolean valid = service.validatePassword(email, password);
        HttpStatus status = Boolean.TRUE.equals((valid)) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN)")
    public ResponseEntity<User> save(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.save(userDTO));
    }

}
