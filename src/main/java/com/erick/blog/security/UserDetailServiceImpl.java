package com.erick.blog.security;

import com.erick.blog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@Transactional
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.erick.blog.domains.entities.User user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getAuthorities()
        );
    }

}
