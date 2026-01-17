package com.automotiveserviceplatform.repository;

import com.automotiveserviceplatform.entity.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {
    Optional<Estimate> findByJobCardId(Long jobCardId);
}
