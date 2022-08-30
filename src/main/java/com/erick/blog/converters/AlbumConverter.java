package com.erick.blog.converters;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.entities.Album;
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

}
