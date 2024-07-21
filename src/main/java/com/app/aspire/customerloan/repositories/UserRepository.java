package com.app.aspire.customerloan.repositories;

import com.app.aspire.customerloan.entities.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}