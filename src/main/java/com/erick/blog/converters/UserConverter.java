package com.erick.blog.converters;

import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User dtoToEntity(UserDTO dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        if (dto.getRoles() != null) {
            entity.setRoles(dto.getRoles().stream()
                    .map(DefaultConverters::roleDtoToEntity).toList());
        }
        if (dto.getPosts() != null) {
            entity.setPosts(dto.getPosts().stream()
                    .map(DefaultConverters::postDtoToEntity).toList());
        }
        if (dto.getComments() != null) {
            entity.setComments(dto.getComments().stream()
                    .map(DefaultConverters::commentDtoToEntity).toList());
        }
        if (dto.getAlbums() != null) {
            entity.setAlbums(dto.getAlbums().stream()
                    .map(DefaultConverters::albumDtoToEntity).toList());
        }
        return entity;
    }

    public UserDTO entityToDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());

        if (entity.getRoles() != null) {
            dto.setRoles(entity.getRoles().stream()
                    .map(DefaultConverters::roleEntityToDto).toList());
        }
        if (entity.getPosts() != null) {
            dto.setPosts(entity.getPosts().stream()
                    .map(DefaultConverters::postEntityToDto).toList());
        }
        if (entity.getComments() != null) {
            dto.setComments(entity.getComments().stream()
                    .map(DefaultConverters::commentEntityToDto).toList());
        }
        if (entity.getAlbums() != null) {
            dto.setAlbums(entity.getAlbums().stream()
                    .map(DefaultConverters::albumEntityToDto).toList());
        }
        return dto;
    }

}
