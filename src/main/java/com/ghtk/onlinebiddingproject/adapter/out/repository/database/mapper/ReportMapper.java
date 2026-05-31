package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AdminEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.AuctionEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportImageEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportResultEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.UserEntity;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImage;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResult;
import jakarta.persistence.EntityManager;

public class ReportMapper {

    private ReportMapper() {
    }

    public static Report toDomain(ReportEntity entity) {
        if (entity == null) return null;
        Report report = new Report(
                entity.getId(),
                entity.getDescription(),
                entity.getReporter().getId(),
                entity.getReported().getId(),
                entity.getAuction() != null ? entity.getAuction().getId() : null
        );
        if (entity.getResultEntity() != null) {
            report.setResult(toDomainResult(entity.getResultEntity()));
        }
        return report;
    }

    public static ReportEntity toEntity(Report domain, EntityManager em) {
        if (domain == null) return null;
        ReportEntity entity;
        if (domain.getId() != null) {
            entity = em.find(ReportEntity.class, domain.getId());
            if (entity == null) {
                entity = new ReportEntity();
                entity.setId(domain.getId());
            }
        } else {
            entity = new ReportEntity();
        }
        entity.setDescription(domain.getDescription());
        entity.setReporter(em.getReference(UserEntity.class, domain.getReporterId()));
        entity.setReported(em.getReference(UserEntity.class, domain.getReportedId()));
        if (domain.getAuctionId() != null) {
            entity.setAuction(em.getReference(AuctionEntity.class, domain.getAuctionId()));
        }
        return entity;
    }

    public static ReportResult toDomainResult(ReportResultEntity entity) {
        if (entity == null) return null;
        return new ReportResult(
                entity.getId(),
                entity.getResult(),
                entity.getAdmin().getId(),
                entity.getReport().getId()
        );
    }

    public static ReportResultEntity toEntityResult(ReportResult domain, EntityManager em) {
        if (domain == null) return null;
        ReportResultEntity entity = new ReportResultEntity();
        entity.setId(domain.getId());
        entity.setResult(domain.getStatus());
        entity.setAdmin(em.getReference(AdminEntity.class, domain.getAdminId()));
        entity.setReport(em.getReference(ReportEntity.class, domain.getReportId()));
        return entity;
    }

    public static ReportImage toDomain(ReportImageEntity entity) {
        if (entity == null) return null;
        return new ReportImage(
                entity.getId(),
                entity.getImageUrl(),
                entity.getPublicId()
        );
    }

    public static ReportImageEntity toEntity(ReportImage domain, ReportEntity reportEntity) {
        if (domain == null) return null;
        ReportImageEntity entity = new ReportImageEntity();
        entity.setId(domain.getId());
        entity.setImageUrl(domain.getImageUrl());
        entity.setPublicId(domain.getPublicId());
        entity.setReport(reportEntity);
        return entity;
    }
}
