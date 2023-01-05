package com.erick.blog.controllers;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.services.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Order(4)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class AlbumControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.album}")
    private String createAlbum;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.truncate.album}")
    private String truncateAlbum;

    @Value("${sql.truncate.user}")
    private String truncateUser;

    @Autowired
    private AlbumService service;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUpBeforeAll() {
        jdbc.execute(createUser);
        jdbc.execute(createAlbum);
    }

    @AfterAll
    void setUpAfterAll() {
        jdbc.execute(truncateAlbum);
        jdbc.execute(truncateUser);
    }

    @Test
    void save() throws Exception {
        AlbumDTO dto = new AlbumDTO();
        dto.setImageUrl("test.url.com");

        mockMvc.perform(post("/albums").param("userId", "1")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/albums").param("userId", "1")
                        .with(httpBasic("java@java.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        assertNotNull(service.findById(1L), "Should return a valid album");
    }

    @Test
    @Sql("/scripts/insertAlbumData.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/albums"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/albums")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/albums/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/albums/{id}", 1L)
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty album");
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/albums/delete-by-id")
                        .param("postId", "1")
                        .param("userEmail", "java@java.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/albums/delete-by-id")
                        .param("albumId", "1")
                        .param("userEmail", "java@java.com")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isNoContent());

        assertThrows(HandlerException.class, () -> service.findById(1L));
    }

}