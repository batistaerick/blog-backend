package com.erick.blog.services;

import java.util.List;
import java.util.Optional;

import com.erick.blog.entities.User;
import com.erick.blog.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public User insertUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        Optional<User> user = userRepository.findByLogin(login);
        return user;
    }

    public Boolean validatePassword(String login, String password) {
        Optional<User> optUser = findByLogin(login);
        if (optUser.isEmpty()) {
            return false;
        }
        Boolean valid = encoder.matches(password, optUser.get().getPassword());
        return valid;
    }
}