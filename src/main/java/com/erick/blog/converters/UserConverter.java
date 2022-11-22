package com.erick.blog.converters;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.HandlerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final PostConverter postConverter;
    private final CommentConverter commentConverter;
    private final AlbumConverter albumConverter;

    public User dtoToEntity(UserDTO dto) {
        try {
            User entity = new User();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    public UserDTO entityToDto(User entity) {
        try {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
