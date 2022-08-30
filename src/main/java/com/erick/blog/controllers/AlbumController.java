package com.erick.blog.controllers;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<List<Album>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find-by-id/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Album> save(@RequestBody AlbumDTO albumDTO, @RequestParam Long userId) {
        return ResponseEntity.ok(service.save(albumDTO, userId));
    }

    @DeleteMapping("/delete-by-id")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN, 'ROLE_USER')")
    public ResponseEntity<Void> deleteById(@RequestParam Long idAlbum, @RequestParam String userEmail) {
        service.deleteById(idAlbum, userEmail);
        return ResponseEntity.noContent().build();
    }

}
