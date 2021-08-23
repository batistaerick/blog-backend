package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.entities.Post;
import com.erick.blog.entities.User;
import com.erick.blog.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value = "/posts/findAll")
    public ResponseEntity<List<Post>> findAll() {
        List<Post> list = postService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/posts/findById/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        Post obj = postService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/posts/findByTittle")
    public ResponseEntity<List<Post>> findByTittle(@PathVariable String search) {
        List<Post> list = postService.findByTittle(search);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/posts/insertPost/{userId}")
    public ResponseEntity<Post> insertPost(@PathVariable Long userId, @RequestBody Post post, MultipartFile file)
            throws NotFoundException {
        Post obj = postService.insertPost(userId, post, file);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/posts/deleteById/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, User user) throws Exception {
        postService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}