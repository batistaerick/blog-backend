package com.erick.blog.converters;

import com.erick.blog.domains.dtos.PostDTO;
import com.erick.blog.domains.entities.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post dtoToEntity(PostDTO dto) {
        Post entity = new Post();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setTitle(dto.getTitle());
        entity.setBody(dto.getBody());
        entity.setImageUrl(dto.getImageUrl());

        if (dto.getComments() != null) {
            entity.setComments(dto.getComments().stream()
                    .map(DefaultConverters::commentDtoToEntity).toList());
        }
        if (dto.getUser() != null) {
            entity.setUser(DefaultConverters.userDtoToEntity(dto.getUser()));
        }
        return entity;
    }

    public PostDTO entityToDto(Post entity) {
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        dto.setBody(entity.getBody());
        dto.setImageUrl(entity.getImageUrl());

        if (entity.getComments() != null) {
            dto.setComments(entity.getComments().stream().map(DefaultConverters::commentEntityToDto).toList());
        }
        if (entity.getUser() != null) {
            dto.setUser(DefaultConverters.userEntityToDto(entity.getUser()));
        }
        return dto;
    }

}
