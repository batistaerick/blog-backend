package com.erick.blog.controllers;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
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

    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<Post>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @PostMapping("/save")
    public ResponseEntity<Post> save(@RequestParam Long userId, @RequestBody PostDTO postDTO) {
        return ResponseEntity.ok(service.save(userId, postDTO));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> delete(@RequestParam Long postId, @RequestParam String userEmail) {
        service.deleteById(postId, userEmail);
        return ResponseEntity.noContent().build();
    }
}