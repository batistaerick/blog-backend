package com.erick.blog.controllers;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.services.UserService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Order(1)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class UserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    @Value("${sql.creation.user}")
    private String createUser;

    @Value("${sql.creation.role}")
    private String createRole;

    @Value("${sql.creation.role-user}")
    private String createRoleUser;

    @Value("${sql.truncate.user}")
    private String truncateUser;

    @Autowired
    private UserService service;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    void setUpBeforeAll() {
        jdbc.execute(createUser);
        jdbc.execute(createRole);
        jdbc.execute(createRoleUser);
    }

    @AfterAll
    void setUpAfterAll() {
        jdbc.execute(truncateUser);
    }

    @Test
    void save() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("Save");
        dto.setEmail("save@save.com");
        dto.setPassword("321");

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/users")
                        .with(httpBasic("java@java.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        assertNotNull(service.findByEmail("save@save.com"), "Should return a valid user");
    }

    @Test
    @Sql("/scripts/insertUserData.sql")
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users/{id}", 1L)
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty user!");
    }

    @Test
    void findByEmail() throws Exception {
        mockMvc.perform(get("/find-by-email/{email}", "java@java.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users/find-by-email/{email}", "java@java.com")
                        .with(httpBasic("java@java.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        assertNotNull(service.findByEmail("java@java.com"), "Should return a non empty user!");
    }

}
