package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportImageEntity;
import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportResultEntity;
import com.ghtk.onlinebiddingproject.constants.ReportResultConstants;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImage;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResult;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultStatus;

import java.util.ArrayList;
import java.util.List;

public class ReportMapper {

    private ReportMapper() {
    }

    public static Report toDomain(ReportEntity entity) {
        if (entity == null) return null;
        Report report = new Report(
                entity.getId(),
                entity.getDescription(),
                entity.getReporterId(),
                entity.getReportedId(),
                entity.getAuctionId()
        );
        return report;
    }

    public static ReportEntity toEntity(Report domain) {
        if (domain == null) return null;
        ReportEntity entity = new ReportEntity();
        entity.setId(domain.getId());
        entity.setDescription(domain.getDescription());
        entity.setReporterId(domain.getReporterId());
        entity.setReportedId(domain.getReportedId());
        entity.setAuctionId(domain.getAuctionId());
        return entity;
    }

    public static ReportResult toDomain(ReportResultEntity entity) {
        if (entity == null) return null;
        return new ReportResult(
                entity.getId(),
                toDomainStatus(entity.getStatus()),
                entity.getAdminId(),
                entity.getReportId()
        );
    }

    public static ReportResultEntity toEntity(ReportResult domain) {
        if (domain == null) return null;
        ReportResultEntity entity = new ReportResultEntity();
        entity.setId(domain.getId());
        entity.setStatus(toEntityStatus(domain.getStatus()));
        entity.setAdminId(domain.getAdminId());
        entity.setReportId(domain.getReportId());
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

    public static ReportImageEntity toEntity(ReportImage domain, Integer reportId) {
        if (domain == null) return null;
        ReportImageEntity entity = new ReportImageEntity();
        entity.setId(domain.getId());
        entity.setImageUrl(domain.getImageUrl());
        entity.setPublicId(domain.getPublicId());
        entity.setReportId(reportId);
        return entity;
    }

    private static ReportResultStatus toDomainStatus(ReportResultConstants status) {
        if (status == null) return ReportResultStatus.REJECTED;
        return switch (status) {
            case ACCEPTED -> ReportResultStatus.ACCEPTED;
            case REJECTED -> ReportResultStatus.REJECTED;
        };
    }

    private static ReportResultConstants toEntityStatus(ReportResultStatus status) {
        if (status == null) return ReportResultConstants.REJECTED;
        return switch (status) {
            case ACCEPTED -> ReportResultConstants.ACCEPTED;
            case REJECTED -> ReportResultConstants.REJECTED;
        };
    }
}
