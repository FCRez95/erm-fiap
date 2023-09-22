package com.example.ermfiap.domain.Users.repository;

import com.example.ermfiap.domain.Users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersSprigDataRepository extends JpaRepository<Users, Long> {
    Users findByEmailEquals(String email);
}
