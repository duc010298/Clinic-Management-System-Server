package com.github.duc010298.cms.repository;

import com.github.duc010298.cms.entity.ReportFormEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportFormRepository extends JpaRepository<ReportFormEntity, UUID> {

    List<ReportFormEntity> findAllByOrderByOrderNumberAsc();

    Optional<ReportFormEntity> findById(UUID id);
//
//    @Query("SELECT MAX(rf.orderNumber) FROM ReportFormEntity rf")
//    Integer getMaxOrderNumber();
}
