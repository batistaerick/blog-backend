package com.erick.blog.converters;

import com.erick.blog.Utils.DefaultConverters;
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
        entity.setComments(dto.getComments().stream().map(DefaultConverters::commentDtoToEntity).toList());
        entity.setUser(DefaultConverters.userDtoToEntity(dto.getUser()));
        return entity;
    }

    public PostDTO entityToDto(Post entity) {
        PostDTO dto = new PostDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        dto.setBody(entity.getBody());
        dto.setImageUrl(entity.getImageUrl());
        dto.setComments(entity.getComments().stream().map(DefaultConverters::commentEntityToDto).toList());
        dto.setUser(DefaultConverters.userEntityToDto(entity.getUser()));
        return dto;
    }

}
