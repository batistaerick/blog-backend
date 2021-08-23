package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.entities.Comment;
import com.erick.blog.entities.User;
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

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/comments/findAll")
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok().body(commentService.findAll());
    }

    @GetMapping(value = "/comments/findById/{id}")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        Comment obj = commentService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/comments/insertComment/{userId}/{postId}")
    public ResponseEntity<Comment> insertComment(@PathVariable Long userId, @PathVariable Long postId,
            @RequestBody Comment comment) throws NotFoundException {
        Comment obj = commentService.insertPost(userId, postId, comment);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/comments/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody User user)
            throws Exception {
        commentService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}