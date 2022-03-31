package com.erick.blog.controllers;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/albums")
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
    public ResponseEntity<Album> insertAlbum(@RequestBody AlbumDTO albumDTO, String url) {
        return ResponseEntity.ok(service.save(albumDTO, url));
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        service.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}