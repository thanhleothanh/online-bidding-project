package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Optional;

public interface ReportResultRepository {
    ReportResult save(ReportResult result);
    Optional<ReportResult> findByReportId(Integer reportId);
}
