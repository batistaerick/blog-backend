package com.erick.blog.controllers;

import com.erick.blog.services.PostService;
import com.erick.blog.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.creation.post}")
    private String createPost;

    @Value("${sql.delete.user}")
    private String deleteUser;

    @Value("${sql.delete.post}")
    private String deletePost;

    @Autowired
    private PostService service;

    @Autowired
    private UserService userService;

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
    }

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/posts/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts/{id}", 1L)
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty post!");
    }

    @Test
    void findByTitle() throws Exception {
        mockMvc.perform(get("/posts/find-by-title/{title}", "Some title"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/posts/find-by-title/{title}", "Some title")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty post!");
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

}