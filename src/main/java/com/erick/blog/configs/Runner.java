package com.erick.blog.configs;

import com.erick.blog.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private static final String CREATE_USER = "INSERT INTO t_user (email, name, password) VALUES ('test@test.com', 'Test', '$2a$12$X9dBo6PQYDoWE/mUtYmXDujuR/GK3..KRmQH8Eysw.TQfIqgd10IC')";
    private static final String CREATE_ROLE = "INSERT INTO t_role (role_name) VALUES ('ROLE_ADMIN'), ('ROLE_USER')";
    private static final String CREATE_ROLE_USER = "INSERT INTO t_users_roles VALUES (1, 1)";

    private final UserRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    @Override
    public void run(String... args) throws Exception {
        if (repository.findByEmail("test@test.com").isEmpty() && environment.getProperty("sql.creation.user") == null) {
            jdbcTemplate.execute(CREATE_USER);
            jdbcTemplate.execute(CREATE_ROLE);
            jdbcTemplate.execute(CREATE_ROLE_USER);
        }
    }

}
