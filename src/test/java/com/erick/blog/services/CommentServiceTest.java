package com.erick.blog.services;

import com.erick.blog.converters.CommentConverter;
import com.erick.blog.domains.entities.Comment;
import com.erick.blog.domains.entities.Post;
import com.erick.blog.domains.entities.User;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class CommentServiceTest {

    @MockBean
    private CommentRepository repository;

    @Autowired
    private CommentService service;

    @Autowired
    private CommentConverter converter;

    private Comment comment;

    @BeforeEach
    void setUpBeforeEach() {
        User user = new User();
        user.setId(1L);
        user.setEmail("erick@erick.com");

        Post post = new Post();
        post.setId(1L);
        post.setDate(Instant.now());
        post.setTitle("Title Test");
        post.setBody("Testing the body");
        post.setImageUrl("www.url-testing.com.br");
        post.setUser(user);

        comment = new Comment();
        comment.setId(1L);
        comment.setText("Testing of the comment");
        comment.setDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 2);
        List<Comment> list = List.of(comment, new Comment());
        Page<Comment> expected = new PageImpl<>(list, pageable, 2);
        when(repository.findAll(pageable)).thenReturn(expected);
        assertIterableEquals(expected, service.findAll(pageable), "Should return a list of comments");
    }

    @Test
    void findById() {
        when(repository.findById(1L))
                .thenThrow(new HandlerException("Comment Not Found."))
                .thenReturn(Optional.of(comment));
        assertThrows(HandlerException.class, () -> service.findById(1L));
        assertEquals(comment, service.findById(1L), "Should return a single comment");
        verify(repository, times(2)).findById(1L);
    }

}