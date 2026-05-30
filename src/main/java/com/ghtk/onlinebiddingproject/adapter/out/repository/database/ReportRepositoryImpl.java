package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ReportMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ReportJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportQuery;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final ReportJpaRepository reportJpaRepository;

    public ReportRepositoryImpl(ReportJpaRepository reportJpaRepository) {
        this.reportJpaRepository = reportJpaRepository;
    }

    @Override
    public Optional<Report> findById(Integer id) {
        return reportJpaRepository.findById(id).map(ReportMapper::toDomain);
    }

    @Override
    @Transactional
    public Report save(Report report) {
        ReportEntity entity = ReportMapper.toEntity(report);
        ReportEntity saved = reportJpaRepository.save(entity);
        return ReportMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        reportJpaRepository.deleteById(id);
    }

    @Override
    public List<Report> find(ReportQuery query) {
        Specification<ReportEntity> spec = buildSpecification(query);
        return reportJpaRepository.findAll(spec).stream()
                .map(ReportMapper::toDomain)
                .toList();
    }

    @Override
    public List<Report> findPaged(ReportQuery query, int page, int size) {
        Specification<ReportEntity> spec = buildSpecification(query);
        return reportJpaRepository.findAll(spec, PageRequest.of(page, size)).stream()
                .map(ReportMapper::toDomain)
                .toList();
    }

    private Specification<ReportEntity> buildSpecification(ReportQuery query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query.getReporterId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("reporterId"), query.getReporterId()));
            }
            if (query.getReportedId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("reportedId"), query.getReportedId()));
            }
            if (query.getAuctionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("auctionId"), query.getAuctionId()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
