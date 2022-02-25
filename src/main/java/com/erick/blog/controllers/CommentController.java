package com.erick.blog.controllers;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/insertComment/{userId}/{postId}")
    public ResponseEntity<Comment> insertComment(@PathVariable Long userId, @PathVariable Long postId,
                                                 @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(service.insertPost(userId, postId, commentDTO));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        service.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}