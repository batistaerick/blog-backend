package com.erick.blog.controllers;

import com.erick.blog.dtos.AlbumDTO;
import com.erick.blog.exceptions.HandlerException;
import com.erick.blog.services.AlbumService;
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
class AlbumControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.creation.album}")
    private String createAlbum;

    @Value("${sql.delete.user}")
    private String deleteUser;

    @Value("${sql.delete.album}")
    private String deleteAlbum;

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
        jdbc.execute(deleteAlbum);
        jdbc.execute(deleteUser);
    }

    @Test
    @Sql("/scripts/insertAlbumData.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/albums"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/albums")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/albums/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/albums/{id}", 1L)
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty album");
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
                        .with(httpBasic("erick@erick.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        assertNotNull(service.findById(1L), "Should return a valid album");
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete("/albums/delete-by-id")
                        .param("postId", "1")
                        .param("userEmail", "erick@erick.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(delete("/albums/delete-by-id")
                        .param("albumId", "1")
                        .param("userEmail", "erick@erick.com")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isNoContent());

        assertThrows(HandlerException.class, () -> service.findById(1L));
    }

}