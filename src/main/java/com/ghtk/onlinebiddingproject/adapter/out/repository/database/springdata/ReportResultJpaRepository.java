package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportResultJpaRepository extends JpaRepository<ReportResultEntity, Integer> {

    Optional<ReportResultEntity> findByReportId(Integer reportId);
}
