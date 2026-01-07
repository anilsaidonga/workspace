package com.automotiveserviceplatform.repository;

import com.automotiveserviceplatform.entity.Tow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TowRepository extends JpaRepository<Tow, Long> {
}
