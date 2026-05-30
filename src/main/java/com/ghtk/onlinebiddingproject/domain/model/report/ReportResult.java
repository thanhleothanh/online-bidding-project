package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Objects;

public class ReportResult {

    private Integer id;
    private ReportResultStatus status;
    private Integer adminId;
    private Integer reportId;

    public ReportResult(Integer id, ReportResultStatus status, Integer adminId, Integer reportId) {
        this.id = id;
        this.status = status;
        this.adminId = adminId;
        this.reportId = reportId;
    }

    public static ReportResult judge(Integer adminId, ReportResultStatus status) {
        return new ReportResult(null, status, adminId, null);
    }

    public boolean isAccepted() { return status != null && status.isAccepted(); }
    public boolean isRejected() { return status != null && status.isRejected(); }

    public Integer getId() { return id; }
    public ReportResultStatus getStatus() { return status; }
    public Integer getAdminId() { return adminId; }
    public Integer getReportId() { return reportId; }
    public void setReportId(Integer reportId) { this.reportId = reportId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportResult that = (ReportResult) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
