package com.erick.blog.converters;

import com.erick.blog.domains.dtos.CommentDTO;
import com.erick.blog.domains.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public Comment dtoToEntity(CommentDTO dto) {
        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setDate(dto.getDate());

        if (dto.getPost() != null) {
            entity.setPost(DefaultConverters.postDtoToEntity(dto.getPost()));
        }
        if (dto.getUser() != null) {
            entity.setUser(DefaultConverters.userDtoToEntity(dto.getUser()));
        }
        return entity;
    }

    public CommentDTO entityToDto(Comment entity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setDate(entity.getDate());

        if (entity.getPost() != null) {
            dto.setPost(DefaultConverters.postEntityToDto(entity.getPost()));
        }
        if (entity.getUser() != null) {
            dto.setUser(DefaultConverters.userEntityToDto(entity.getUser()));
        }
        return dto;
    }

}
