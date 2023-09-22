package com.example.ermfiap.domain.Users.repository;

import com.example.ermfiap.domain.Users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersSprigDataRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailEquals(String email);
}
