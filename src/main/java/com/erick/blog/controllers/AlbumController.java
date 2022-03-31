package com.erick.blog.controllers;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @GetMapping("/findAll")
    public ResponseEntity<List<Album>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<Album> save(@RequestBody AlbumDTO albumDTO, @RequestParam Long userId) {
        return ResponseEntity.ok(service.save(albumDTO, userId));
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Void> deleteById(@PathVariable Long idAlbum, @RequestParam String userEmail) {
        service.deleteById(idAlbum, userEmail);
        return ResponseEntity.noContent().build();
    }
}