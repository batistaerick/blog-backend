package com.erick.blog.services;

import java.util.Optional;

import com.erick.blog.data.UserDetailData;
import com.erick.blog.entities.User;
import com.erick.blog.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByLogin(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User [ " + username + " ] Not Found");
        }
        return new UserDetailData(user);
    }
}