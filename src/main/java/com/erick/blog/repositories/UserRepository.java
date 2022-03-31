package com.erick.blog.repositories;

import java.util.Optional;

import com.erick.blog.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}