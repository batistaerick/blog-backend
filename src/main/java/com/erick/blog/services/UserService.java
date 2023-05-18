package com.erick.blog.services;

import com.erick.blog.converters.UserConverter;
import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserConverter converter;
    private final PasswordEncoder encoder;

    public UserDTO save(UserDTO userDTO) {
        if (repository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new HandlerException("User already exists");
        }
        User user = converter.dtoToEntity(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        return converter.entityToDto(repository.save(user));
    }

    public UserDTO update(UserDTO userDTO, Authentication authentication) {
        boolean isNotEquals = !authentication.getName().equals(userDTO.getEmail());
        if (isNotEquals) {
            throw new HandlerException("You can only update yourself!");
        }
        userDTO.setPassword(findByEmail(userDTO.getEmail()).getPassword());
        User user = converter.dtoToEntity(userDTO);
        return converter.entityToDto(repository.save(user));
    }

    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(converter::entityToDto).toList();
    }

    public UserDTO findById(Long id) {
        return repository.findById(id).map(converter::entityToDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public UserDTO findByEmail(String login) {
        return repository.findByEmail(login).map(converter::entityToDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public void deleteById(Long id, Authentication authentication) {
        try {
            boolean isNotEquals = !findById(id).getEmail().equals(authentication.getName());
            if (isNotEquals) {
                throw new HandlerException("You can only delete yourself!");
            }
            repository.deleteById(id);
        } catch (Exception exception) {
            throw new HandlerException(exception);
        }
    }

}
