package com.example.restapi.repo;

import com.example.restapi.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepo extends JpaRepository<Auth, Long> {

}
