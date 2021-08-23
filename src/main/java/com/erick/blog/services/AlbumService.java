package com.erick.blog.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import com.erick.blog.entities.Album;
import com.erick.blog.entities.User;
import com.erick.blog.repositories.AlbumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Album findById(Long id) {
        return albumRepository.findById(id).get();
    }

    public Album insertAlbum(Album album, MultipartFile file) {
        if (file != null) {
            try {
                album.setImage(toObjects(file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return albumRepository.save(album);
    }

    private Byte[] toObjects(byte[] bytes) {
        return IntStream.range(0, bytes.length).mapToObj(i -> bytes[i]).toArray(Byte[]::new);
    }

    public void deleteById(Long id, User user) throws Exception {
        if (albumRepository.findById(id).get().getUserAlbums().getLogin().equals(user.getLogin())) {
            albumRepository.deleteById(id);
        } else {
            throw new Exception("Only the creator can delete this comment");
        }
    }
}