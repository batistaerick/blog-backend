package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.services.CommentService;

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
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok().body(commentService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        Comment obj = commentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/insertComment/{userId}/{postId}")
    public ResponseEntity<Comment> insertComment(@PathVariable Long userId, @PathVariable Long postId,
            @RequestBody CommentDTO commentDTO) {
        Comment obj = commentService.insertPost(userId, postId, commentDTO);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        commentService.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}