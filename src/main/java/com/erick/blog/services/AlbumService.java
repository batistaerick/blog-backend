package com.erick.blog.services;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.exceptions.AlbumException;
import com.erick.blog.repositories.AlbumRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private UserService userService;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlbumException("Album not found!"));
    }

    public Album save(AlbumDTO albumDTO, Long userId) {
        Album album = dtoToEntity(albumDTO);
        album.setUser(userService.findById(userId));
        return repository.save(album);
    }

    public void deleteById(Long idAlbum, String userEmail) {
        try {
            if (!findById(idAlbum).getUser().getEmail().equals(userEmail)) {
                throw new AlbumException("Only creator can delete this comment!");
            }
            repository.deleteById(idAlbum);
        } catch (Exception e) {
            throw new AlbumException(e);
        }
    }

    public Album dtoToEntity(AlbumDTO dto) {
        try {
            Album entity = new Album();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new AlbumException(e);
        }
    }
}