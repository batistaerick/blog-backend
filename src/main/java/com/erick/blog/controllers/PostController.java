package com.erick.blog.controllers;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findByTittle")
    public ResponseEntity<List<Post>> findByTittle(@PathVariable String search) {
        return ResponseEntity.ok(service.findByTittle(search));
    }

    @PostMapping("/save/{userId}")
    public ResponseEntity<Post> insertPost(@PathVariable Long userId, @RequestBody PostDTO postDTO, String url) {
        return ResponseEntity.ok(service.save(userId, postDTO, url));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        service.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}