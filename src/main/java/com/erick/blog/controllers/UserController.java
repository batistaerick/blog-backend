package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/validatePassword")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String login, @RequestParam String password) {
        Boolean valid = service.validatePassword(login, password);
        HttpStatus status = Boolean.TRUE.equals((valid)) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    @PutMapping("/insertUser")
    public ResponseEntity<User> insertUser(@RequestBody UserDTO userDTO) {
        User obj = service.insertUser(userDTO);
        return ResponseEntity.ok().body(obj);
    }
}