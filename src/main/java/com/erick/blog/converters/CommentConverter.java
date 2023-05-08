package com.erick.blog.converters;

import com.erick.blog.domains.dtos.CommentDTO;
import com.erick.blog.domains.entities.Comment;
import com.erick.blog.exceptions.HandlerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public Comment dtoToEntity(CommentDTO dto) {
        try {
            Comment entity = new Comment();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public CommentDTO entityToDto(Comment entity) {
        try {
            CommentDTO dto = new CommentDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
