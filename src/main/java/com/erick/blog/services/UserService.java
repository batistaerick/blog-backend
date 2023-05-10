package com.erick.blog.services;

import com.erick.blog.converters.UserConverter;
import com.erick.blog.domains.dtos.UserDTO;
import com.erick.blog.domains.entities.User;
import com.erick.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public User save(UserDTO userDTO) {
        User user = converter.dtoToEntity(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public User findByEmail(String login) {
        return repository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

}
