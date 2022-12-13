package com.erick.blog.controllers;

import com.erick.blog.dtos.CommentDTO;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.services.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.creation.post}")
    private String createPost;

    @Value("${sql.creation.comment}")
    private String createComment;

    @Value("${sql.delete.user}")
    private String deleteUser;

    @Value("${sql.delete.post}")
    private String deletePost;

    @Value("${sql.delete.comment}")
    private String deleteComment;

    @Autowired
    private CommentService service;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUpBeforeAll() {
        jdbc.execute(createUser);
        jdbc.execute(createPost);
        jdbc.execute(createComment);
    }

    @AfterAll
    void setUpAfterAll() {
        jdbc.execute(deleteComment);
        jdbc.execute(deletePost);
        jdbc.execute(deleteUser);
    }

    @Test
    void save() throws Exception {
        CommentDTO dto = new CommentDTO();
        dto.setText("Comment stuffs");
        dto.setDate(Instant.now());

        mockMvc.perform(post("/comments")
                        .param("userId", "1")
                        .param("postId", "1")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/comments")
                        .param("userId", "1")
                        .param("postId", "1")
                        .with(httpBasic("erick@erick.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        assertNotNull(service.findById(1L), "Should return a valid post");
    }

    @Test
    @Sql("/scripts/insertCommentData.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/comments"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/comments")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/comments/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/comments/{id}", 1L)
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty post!");
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/comments/delete-by-id")
                        .param("commentId", "1")
                        .param("userEmail", "erick@erick.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/comments/delete-by-id")
                        .param("commentId", "1")
                        .param("userEmail", "erick@erick.com")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isNoContent());

        assertThrows(HandlerException.class, () -> service.findById(1L));
    }

}