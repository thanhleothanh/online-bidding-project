package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ReportMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportResultEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ReportResultJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResult;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ReportResultRepositoryImpl implements ReportResultRepository {

    private final ReportResultJpaRepository reportResultJpaRepository;
    private final EntityManager entityManager;

    public ReportResultRepositoryImpl(ReportResultJpaRepository reportResultJpaRepository, EntityManager entityManager) {
        this.reportResultJpaRepository = reportResultJpaRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public ReportResult save(ReportResult result) {
        ReportResultEntity entity = ReportMapper.toEntityResult(result, entityManager);
        ReportResultEntity saved = reportResultJpaRepository.save(entity);
        return ReportMapper.toDomainResult(saved);
    }

    @Override
    public Optional<ReportResult> findByReportId(Integer reportId) {
        return reportResultJpaRepository.findByReportId(reportId)
                .map(ReportMapper::toDomainResult);
    }
}
