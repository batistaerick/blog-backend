package com.erick.blog.services;

import com.erick.blog.converters.AlbumConverter;
import com.erick.blog.domains.dtos.AlbumDTO;
import com.erick.blog.domains.entities.Album;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository repository;
    private final AlbumConverter converter;
    private final UserService userService;

    public Album save(AlbumDTO albumDTO, Long userId) {
        Album album = converter.dtoToEntity(albumDTO);
        album.setUser(userService.findById(userId));
        return repository.save(album);
    }

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HandlerException("Album not found!"));
    }

    public void deleteById(Long albumId, String userEmail) {
        try {
            if (!findById(albumId).getUser().getEmail().equals(userEmail)) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(albumId);
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
