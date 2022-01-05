package com.erick.blog.services;

import java.util.List;
import java.util.Optional;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.exceptions.DeleteException;
import com.erick.blog.repositories.AlbumRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(Long id) {
        Optional<Album> album = repository.findById(id);
        return album.isPresent() ? album.get() : null;
    }

    public Album insertAlbum(AlbumDTO albumDTO, String image) {
        if (image != null) {
            albumDTO.setImage(image);
        }
        Album album = new Album();
        BeanUtils.copyProperties(albumDTO, album);

        return repository.save(album);
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (findById(id).getUserAlbums().getLogin().equals(userDTO.getLogin())) {
                repository.deleteById(id);
            } else {
                throw new DeleteException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}