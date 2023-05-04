package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.User;
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}