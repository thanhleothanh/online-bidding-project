package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Report {

    private Integer id;
    private String description;
    private Integer reporterId;
    private Integer reportedId;
    private Integer auctionId;
    private final List<ReportImage> images;
    private ReportResult result;

    public Report(Integer id, String description, Integer reporterId, Integer reportedId, Integer auctionId) {
        this.id = id;
        this.description = description;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
        this.auctionId = auctionId;
        this.images = new ArrayList<>();
    }

    public static Report create(Integer reporterId, Integer reportedId, Integer auctionId, String description) {
        if (reporterId.equals(reportedId)) {
            throw new IllegalArgumentException("Cannot report yourself");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }
        return new Report(null, description, reporterId, reportedId, auctionId);
    }

    public boolean canBeJudgedBy(Integer adminId) {
        return true;
    }

    public boolean hasResult() {
        return result != null;
    }

    public void addImage(ReportImage image) {
        images.add(image);
    }

    public List<ReportImage> getImages() {
        return List.copyOf(images);
    }

    public void setResult(ReportResult result) {
        this.result = result;
    }

    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public Integer getReporterId() { return reporterId; }
    public Integer getReportedId() { return reportedId; }
    public Integer getAuctionId() { return auctionId; }
    public ReportResult getResult() { return result; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
