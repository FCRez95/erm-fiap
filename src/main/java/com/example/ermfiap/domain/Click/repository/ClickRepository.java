package com.example.ermfiap.domain.Click.repository;

import com.example.ermfiap.domain.Click.entity.Click;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClickRepository extends JpaRepository<Click, Long> {
    Optional<Click> findByClickToken(String token);
}
