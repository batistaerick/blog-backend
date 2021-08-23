package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.entities.Album;
import com.erick.blog.entities.User;
import com.erick.blog.services.AlbumService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping(value = "/albums/findAll")
    public ResponseEntity<List<Album>> findAll() {
        List<Album> list = albumService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/albums/findById/{id}")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(albumService.findById(id));
    }

    @PostMapping(value = "/albums/insertAlbum")
    public ResponseEntity<Album> insertAlbum(@RequestBody Album album, MultipartFile file) {
        Album obj = albumService.insertAlbum(album, file);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping(value = "/albums/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody User user) throws Exception {
        albumService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}