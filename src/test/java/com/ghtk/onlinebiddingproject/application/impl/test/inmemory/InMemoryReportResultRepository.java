package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.report.ReportResult;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryReportResultRepository implements ReportResultRepository {

    private final Map<Integer, ReportResult> results = new HashMap<>();
    private final Map<Integer, ReportResult> byReportId = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public ReportResult save(ReportResult result) {
        if (result.getId() == null) {
            int id = nextId.getAndIncrement();
            ReportResult newResult = new ReportResult(id, result.getStatus(), result.getAdminId(), result.getReportId());
            results.put(id, newResult);
            if (result.getReportId() != null) {
                byReportId.put(result.getReportId(), newResult);
            }
            return newResult;
        }
        results.put(result.getId(), result);
        if (result.getReportId() != null) {
            byReportId.put(result.getReportId(), result);
        }
        return result;
    }

    @Override
    public Optional<ReportResult> findByReportId(Integer reportId) {
        return Optional.ofNullable(byReportId.get(reportId));
    }
}
