package com.erick.blog.services;

import com.erick.blog.converters.PostConverter;
import com.erick.blog.entities.Post;
import com.erick.blog.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.PostRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
class PostServiceTest {

    @MockBean
    private PostRepository repository;

    @Autowired
    private PostService service;

    @Autowired
    private PostConverter converter;

    private Post post;

    @BeforeAll
    static void setUpBeforeAll() {
    }

    @AfterAll
    static void setUpAfterAll() {
    }

    @BeforeEach
    void setUpBeforeEach() {
        User user = new User();
        user.setId(1L);
        user.setEmail("erick@erick.com");
        post = new Post();
        post.setId(1L);
        post.setDate(Instant.now());
        post.setTitle("Title Test");
        post.setBody("Testing the body");
        post.setImageUrl("www.url-testing.com.br");
        post.setUser(user);
    }

    @Test
    void findAll() {
        List<Post> expected = List.of(post, new Post());
        when(repository.findAll()).thenReturn(expected);
        assertIterableEquals(expected, service.findAll(), "Should return a list of posts");
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(1L))
                .thenThrow(new HandlerException("Post Not Found."))
                .thenReturn(Optional.of(post));

        assertThrows(HandlerException.class, () -> service.findById(1L));
        assertEquals(post, service.findById(1L), "Should return a single post");

        verify(repository, times(2)).findById(1L);
    }

    @Test
    void findByTitle() {
        Post entity = new Post();
        entity.setId(2L);
        entity.setTitle("Another Title");
        List<Post> expected = List.of(post, entity);

        when(repository.findAll()).thenReturn(expected);

        assertIterableEquals(expected, service.findByTitle("Title"),
                "Should be equal");
    }

}
