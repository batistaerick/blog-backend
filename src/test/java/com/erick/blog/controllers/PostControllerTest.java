package com.erick.blog.controllers;

import com.erick.blog.domains.dtos.PostDTO;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.services.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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

@Order(2)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class PostControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.creation.post}")
    private String createPost;

    @Value("${sql.delete.post}")
    private String deletePost;

    @Value("${sql.delete.user}")
    private String deleteUser;

    @Value("${sql.alterTable.post}")
    private String restartPostIdentity;

    @Value("${sql.alterTable.user}")
    private String restartUserIdentity;

    @Autowired
    private PostService service;

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
    }

    @AfterAll
    void setUpAfterAll() {
        jdbc.execute(deletePost);
        jdbc.execute(deleteUser);
        jdbc.execute(restartPostIdentity);
        jdbc.execute(restartUserIdentity);
    }

    @Test
    void save() throws Exception {
        PostDTO dto = new PostDTO();
        dto.setBody("Hi body");
        dto.setImageUrl("stuffs.stuffs");
        dto.setDate(Instant.now());
        dto.setTitle("Hi title");

        mockMvc.perform(post("/posts").param("userId", "1")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/posts").param("userId", "1")
                        .with(httpBasic("java@java.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        assertNotNull(service.findById(1L), "Should return a valid post");
    }

    @Test
    @Sql("/scripts/insertPostData.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/posts?page=0&size=2"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts?page=0&size=2")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts/{id}", 1L)
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty post!");
    }

    @Test
    void findByTitle() throws Exception {
        mockMvc.perform(get("/posts/find-by-title/{title}", "Some title"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts/find-by-title/{title}", "Some title")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty post!");
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/posts/delete-by-id")
                        .param("postId", "1")
                        .param("userEmail", "java@java.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/posts/delete-by-id")
                        .param("postId", "1")
                        .param("userEmail", "java@java.com")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isNoContent());

        assertThrows(HandlerException.class, () -> service.findById(1L));
    }

}