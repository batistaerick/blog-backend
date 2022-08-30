package com.erick.blog.services;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HandlerException("User not found."));
    }

    public User save(UserDTO userDTO) {
        User user = dtoToEntity(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User findByEmail(String login) {
        return repository.findByEmail(login).orElseThrow(() -> new HandlerException("User not found."));
    }

    public Boolean validatePassword(String login, String password) {
        return encoder.matches(password, findByEmail(login).getPassword());
    }

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