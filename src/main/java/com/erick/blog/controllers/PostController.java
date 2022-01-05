package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Post;
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

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Post>> findAll() {
        List<Post> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        Post obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/findByTittle")
    public ResponseEntity<List<Post>> findByTittle(@PathVariable String search) {
        List<Post> list = service.findByTittle(search);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/insertPost/{userId}")
    public ResponseEntity<Post> insertPost(@PathVariable Long userId, @RequestBody PostDTO postDTO, String url) {
        Post obj = service.insertPost(userId, postDTO, url);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        service.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}