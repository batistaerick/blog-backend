package com.erick.blog.controllers;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
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

    @PostMapping("/save")
    public ResponseEntity<Comment> save(@RequestParam Long userId, @RequestParam Long postId,
                                        @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(service.save(userId, postId, commentDTO));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@RequestParam Long idComment, @RequestParam String userEmail) {
        service.deleteById(idComment, userEmail);
        return ResponseEntity.noContent().build();
    }
}