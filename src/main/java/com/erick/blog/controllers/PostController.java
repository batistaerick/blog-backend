package com.erick.blog.controllers;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Post> save(@RequestParam Long userId, @RequestBody PostDTO postDTO) {
        Post post = service.save(userId, postDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(uri).body(post);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Post>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-by-title/{title}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Post>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteById(@RequestParam Long postId, @RequestParam String userEmail) {
        service.deleteById(postId, userEmail);
        return ResponseEntity.noContent().build();
    }

}
