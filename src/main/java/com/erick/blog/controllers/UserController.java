package com.erick.blog.controllers;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/validatePassword")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String login, @RequestParam String password) {
        Boolean valid = service.validatePassword(login, password);
        HttpStatus status = Boolean.TRUE.equals((valid)) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    @PutMapping("/insertUser")
    public ResponseEntity<User> insertUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.insertUser(userDTO));
    }
}