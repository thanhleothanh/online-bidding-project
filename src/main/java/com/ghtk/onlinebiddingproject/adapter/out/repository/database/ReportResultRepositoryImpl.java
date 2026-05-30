package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ReportMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportResultEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ReportResultJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResult;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ReportResultRepositoryImpl implements ReportResultRepository {

    private final ReportResultJpaRepository reportResultJpaRepository;

    public ReportResultRepositoryImpl(ReportResultJpaRepository reportResultJpaRepository) {
        this.reportResultJpaRepository = reportResultJpaRepository;
    }

    @Override
    @Transactional
    public ReportResult save(ReportResult result) {
        ReportResultEntity entity = ReportMapper.toEntity(result);
        ReportResultEntity saved = reportResultJpaRepository.save(entity);
        return ReportMapper.toDomain(saved);
    }

    @Override
    public Optional<ReportResult> findByReportId(Integer reportId) {
        return reportResultJpaRepository.findByReportId(reportId)
                .map(ReportMapper::toDomain);
    }
}
