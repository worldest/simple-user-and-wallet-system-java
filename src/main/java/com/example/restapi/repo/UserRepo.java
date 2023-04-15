package com.example.restapi.repo;

import com.example.restapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
    public Optional<Users> findByEmail(String email);
}
