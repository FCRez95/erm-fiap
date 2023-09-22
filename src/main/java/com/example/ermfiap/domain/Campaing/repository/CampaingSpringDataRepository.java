package com.example.ermfiap.domain.Campaing.repository;

import com.example.ermfiap.domain.Campaing.entity.CampaingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CampaingSpringDataRepository extends JpaRepository<CampaingEntity, Long>  {
    Optional<List<CampaingEntity>> findByIdUserEquals(Long idUser);
}
