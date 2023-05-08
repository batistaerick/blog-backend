package com.erick.blog.converters;

import com.erick.blog.domains.dtos.AlbumDTO;
import com.erick.blog.domains.entities.Album;
import com.erick.blog.exceptions.HandlerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AlbumConverter {

    public Album dtoToEntity(AlbumDTO dto) {
        try {
            Album entity = new Album();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public AlbumDTO entityToDto(Album entity) {
        try {
            AlbumDTO dto = new AlbumDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
