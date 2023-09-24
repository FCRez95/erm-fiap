package com.example.ermfiap.domain.Click.repository;

import com.example.ermfiap.domain.Click.entity.Click;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClickRepository extends JpaRepository<Click, Long> {
    Optional<Click> findByClickToken(String token);
    Optional<Integer> countByIdCampaing(Long idCampaing);
    Optional<Integer> countByIdCampaingAndPaidClick(Long idCampaing, Boolean paidClick);
    @Query("select sum(income) from Click where idCampaing = :idCampaing")
    Optional<Double> sumIncome(@Param("idCampaing") Long idCampaing);
}
