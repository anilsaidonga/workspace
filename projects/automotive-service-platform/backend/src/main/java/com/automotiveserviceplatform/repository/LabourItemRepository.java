package com.automotiveserviceplatform.repository;

import com.automotiveserviceplatform.entity.LabourItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabourItemRepository extends JpaRepository<LabourItem, Long> {
}
