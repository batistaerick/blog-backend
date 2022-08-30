package com.erick.blog.converters;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.HandlerException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User dtoToEntity(UserDTO dto) {
        try {
            User entity = new User();
            BeanUtils.copyProperties(dto, entity);
            return entity;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

}
