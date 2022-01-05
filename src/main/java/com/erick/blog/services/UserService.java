package com.erick.blog.services;

import java.util.List;
import java.util.Optional;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.entities.User;
import com.erick.blog.repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.isPresent() ? user.get() : null;
    }

    public User insertUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(encoder.encode(user.getPassword()));

        return repository.save(user);
    }

    public User findByLogin(String login) {
        Optional<User> user = repository.findByLogin(login);
        return user.isPresent() ? user.get() : null;
    }

    public Boolean validatePassword(String login, String password) {
        User user = findByLogin(login);
        if (user == null) {
            return false;
        }
        return encoder.matches(password, user.getPassword());
    }
}