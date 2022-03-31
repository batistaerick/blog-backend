package com.erick.blog.services;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.Album;
import com.erick.blog.exceptions.AlbumException;
import com.erick.blog.exceptions.UserException;
import com.erick.blog.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository repository;

    public List<Album> findAll() {
        return repository.findAll();
    }

    public Album findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlbumException("Album not found!"));
    }

    public Album save(AlbumDTO albumDTO, String image) {
        if (image != null) {
            albumDTO.setImage(image);
        }
        return repository.save(dtoToEntity(albumDTO));
    }

    public void deleteById(Long id, UserDTO userDTO) {
        try {
            if (!findById(id).getUserAlbums().getLogin().equals(userDTO.getLogin())) {
                throw new AlbumException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception e) {
            throw new AlbumException(e.getMessage());
        }
    }

    public Album dtoToEntity(AlbumDTO dto) {
        try {
            Album entity = new Album();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }
}