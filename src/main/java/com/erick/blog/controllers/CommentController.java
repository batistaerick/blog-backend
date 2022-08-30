package com.erick.blog.controllers;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.entities.Comment;
import com.erick.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<List<Comment>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find-by-id/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Comment> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Comment> save(@RequestParam Long userId, @RequestParam Long postId,
                                        @RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(service.save(userId, postId, commentDTO));
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Void> deleteById(@RequestParam Long idComment, @RequestParam String userEmail) {
        service.deleteById(idComment, userEmail);
        return ResponseEntity.noContent().build();
    }

}
