package com.erick.blog.services;

import com.erick.blog.converters.UserConverter;
import com.erick.blog.domains.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
class UserServiceTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Autowired
    private UserConverter converter;

    @Autowired
    private PasswordEncoder encoder;

    private User user;

    @BeforeEach
    void setUpBeforeEach() {
        user = new User();
        user.setId(1L);
        user.setName("Erick");
        user.setEmail("erick@erick.com");
        user.setPassword(encoder.encode("123"));
    }

    @Test
    void findAll() {
        List<User> expected = List.of(user, new User());
        when(repository.findAll()).thenReturn(expected);
        assertIterableEquals(expected, service.findAll(), "Should return a list of users");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L))
                .thenThrow(new HandlerException("User Not Found."))
                .thenReturn(Optional.of(user));
        assertThrows(HandlerException.class, () -> service.findById(1L));
        assertEquals(user, service.findById(1L), "Should return a single user");
        verify(repository, times(2)).findById(1L);
    }

    @Test
    void findByEmail() {
        when(repository.findByEmail("erick@erick.com")).thenReturn(Optional.of(user));
        assertEquals(user, service.findByEmail("erick@erick.com"),
                "Should return a single user");
    }

}
