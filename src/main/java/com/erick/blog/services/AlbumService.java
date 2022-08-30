package com.erick.blog.services;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository repository;
    private final UserService userService;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HandlerException("Album not found!"));
    }

    public Album save(AlbumDTO albumDTO, Long userId) {
        Album album = dtoToEntity(albumDTO);
        album.setUser(userService.findById(userId));
        return repository.save(album);
    }

    public void deleteById(Long idAlbum, String userEmail) {
        try {
            if (!findById(idAlbum).getUser().getEmail().equals(userEmail)) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(idAlbum);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public Album dtoToEntity(AlbumDTO dto) {
        try {
            Album entity = new Album();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }
}