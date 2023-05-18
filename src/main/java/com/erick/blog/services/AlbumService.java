package com.erick.blog.services;

import com.erick.blog.converters.AlbumConverter;
import com.erick.blog.domains.dtos.AlbumDTO;
import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.Album;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository repository;
    private final AlbumConverter converter;
    private final UserService userService;

    public AlbumDTO save(AlbumDTO albumDTO, Authentication authentication) {
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        albumDTO.setUser(userDTO);
        Album album = converter.dtoToEntity(albumDTO);
        return converter.entityToDto(repository.save(album));
    }

    public AlbumDTO update(AlbumDTO albumDTO, Authentication authentication) {
        boolean isNotEquals = !findById(albumDTO.getId()).getUser().getEmail().equals(authentication.getName());
        if (isNotEquals) {
            throw new HandlerException("Only creator can update this comment!");
        }
        UserDTO userDTO = userService.findByEmail(authentication.getName());
        albumDTO.setUser(userDTO);
        Album album = converter.dtoToEntity(albumDTO);
        return converter.entityToDto(repository.save(album));
    }

    public List<AlbumDTO> findAll() {
        return repository.findAll().stream().map(converter::entityToDto).toList();
    }

    public AlbumDTO findById(Long id) {
        return repository.findById(id).map(converter::entityToDto)
                .orElseThrow(() -> new HandlerException("Album not found!"));
    }

    public void deleteById(Long id, Authentication authentication) {
        try {
            boolean isNotEquals = !findById(id).getUser().getEmail().equals(authentication.getName());
            if (isNotEquals) {
                throw new HandlerException("Only creator can delete this comment!");
            }
            repository.deleteById(id);
        } catch (Exception exception) {
            throw new HandlerException(exception);
        }
    }

}
