package com.erick.blog.converters;

import com.erick.blog.dtos.PostDTO;
import com.erick.blog.entities.Post;
import com.erick.blog.exceptions.HandlerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post dtoToEntity(PostDTO dto) {
        try {
            Post entity = new Post();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public PostDTO entityToDto(Post entity) {
        try {
            PostDTO dto = new PostDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
