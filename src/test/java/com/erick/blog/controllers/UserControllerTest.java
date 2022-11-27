package com.erick.blog.controllers;

import com.erick.blog.dtos.UserDTO;
import com.erick.blog.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
    @Value("${sql.creation.user}")
    private String createUser;
    @Value("${sql.creation.role}")
    private String createRole;
    @Value("${sql.creation.role-user}")
    private String createRoleUser;
    @Value("${sql.delete.role}")
    private String deleteRole;
    @Value("${sql.delete.role-user}")
    private String deleteRoleUser;
    @Value("${sql.delete.user}")
    private String deleteUser;

    @Autowired
    private UserService service;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpBeforeAll() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("email", "erick@erick.com");
        request.setParameter("name", "Erick");
        request.setParameter("password", "123");
    }

    @BeforeEach
    void setUpBeforeEach() {
        jdbc.execute(createUser);
        jdbc.execute(createRole);
        jdbc.execute(createRoleUser);
    }

    @AfterEach
    void setUpAfterEach() {
        jdbc.execute(deleteRoleUser);
        jdbc.execute(deleteRole);
        jdbc.execute(deleteUser);
    }

    @Test
    void save() throws Exception {
        UserDTO user = new UserDTO();
        user.setName("Testing");
        user.setEmail("testing@testing.com");
        user.setPassword("321");

        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(post("/users")
                        .with(httpBasic("erick@erick.com", "password"))
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());

        assertNotNull(service.findByEmail("testing@testing.com"), "Should return a valide user");
    }

    @Sql("/insertData.sql")
    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users").with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void findById() throws Exception {
        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users/{id}", 1L)
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        assertNotNull(service.findById(1L), "Should return a non empty user!");
    }

    @Test
    void findByEmail() throws Exception {
        mockMvc.perform(get("/find-by-email/{email}", "erick@erick.com"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/users/find-by-email/{email}", "erick@erick.com")
                        .with(httpBasic("erick@erick.com", "password")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        assertNotNull(service.findByEmail("erick@erick.com"), "Should return a non empty user!");
    }

}
