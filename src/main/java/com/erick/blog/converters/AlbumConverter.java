package com.erick.blog.converters;

import com.erick.blog.domains.dtos.AlbumDTO;
import com.erick.blog.domains.entities.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    public Album dtoToEntity(AlbumDTO dto) {
        Album entity = new Album();
        entity.setId(dto.getId());
        entity.setImageUrl(dto.getImageUrl());

        if (dto.getUser() != null) {
            entity.setUser(DefaultConverters.userDtoToEntity(dto.getUser()));
        }
        return entity;
    }

    public AlbumDTO entityToDto(Album entity) {
        AlbumDTO dto = new AlbumDTO();
        dto.setId(entity.getId());
        dto.setImageUrl(entity.getImageUrl());

        if (entity.getUser() != null) {
            dto.setUser(DefaultConverters.userEntityToDto(entity.getUser()));
        }
        return dto;
    }

}
