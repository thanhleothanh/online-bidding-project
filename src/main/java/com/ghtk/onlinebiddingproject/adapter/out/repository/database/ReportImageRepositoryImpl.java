package com.ghtk.onlinebiddingproject.adapter.out.repository.database;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper.ReportMapper;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportImageEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata.ReportImageJpaRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImage;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImageRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class ReportImageRepositoryImpl implements ReportImageRepository {

    private final ReportImageJpaRepository reportImageJpaRepository;

    public ReportImageRepositoryImpl(ReportImageJpaRepository reportImageJpaRepository) {
        this.reportImageJpaRepository = reportImageJpaRepository;
    }

    @Override
    @Transactional
    public ReportImage save(ReportImage image) {
        ReportImageEntity entity = new ReportImageEntity();
        entity.setId(image.getId());
        entity.setImageUrl(image.getImageUrl());
        entity.setPublicId(image.getPublicId());
        ReportImageEntity saved = reportImageJpaRepository.save(entity);
        return ReportMapper.toDomain(saved);
    }

    @Override
    public Optional<ReportImage> findById(Integer id) {
        return reportImageJpaRepository.findById(id)
                .map(ReportMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        reportImageJpaRepository.deleteById(id);
    }
}
