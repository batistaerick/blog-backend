package com.erick.blog.controllers;

import java.util.List;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Album;
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

@RestController
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Album>> findAll() {
        List<Album> list = albumService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Album> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(albumService.findById(id));
    }

    @PostMapping("/insertAlbum")
    public ResponseEntity<Album> insertAlbum(@RequestBody AlbumDTO albumDTO, String url) {
        Album obj = albumService.insertAlbum(albumDTO, url);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        albumService.deleteById(id, userDTO);
        return ResponseEntity.noContent().build();
    }
}