package com.example.ecommerce.repository;

import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their username
    Optional<User> findByUsername(String username);
}