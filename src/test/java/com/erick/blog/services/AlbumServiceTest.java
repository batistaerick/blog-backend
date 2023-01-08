package com.erick.blog.services;

import com.erick.blog.converters.AlbumConverter;
import com.erick.blog.entities.Album;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.AlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class AlbumServiceTest {

    @MockBean
    private AlbumRepository repository;

    @Autowired
    private AlbumService service;

    @Autowired
    private AlbumConverter converter;

    private Album album;

    @BeforeEach
    void setUpBeforeEach() {
        User user = new User();
        user.setId(1L);
        user.setEmail("erick@erick.com");
        album = new Album();
        album.setId(1L);
        album.setImageUrl("www.url-testing.com.br");
        album.setUser(user);
    }

    @Test
    void findAll() {
        List<Album> expected = List.of(album, new Album());
        when(repository.findAll()).thenReturn(expected);
        assertIterableEquals(expected, service.findAll(), "Should return a list of albums");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L))
                .thenThrow(new HandlerException("Album Not Found."))
                .thenReturn(Optional.of(album));

        assertThrows(HandlerException.class, () -> service.findById(1L));
        assertEquals(album, service.findById(1L), "Should return a single album");

        verify(repository, times(2)).findById(1L);
    }

}