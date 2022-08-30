package com.erick.blog.converters;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.entities.Comment;
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

}
