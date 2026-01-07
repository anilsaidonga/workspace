package com.automotiveserviceplatform.repository;

import com.automotiveserviceplatform.entity.PartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartItemRepository extends JpaRepository<PartItem, Long> {
}
