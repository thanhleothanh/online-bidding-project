package com.ghtk.onlinebiddingproject.application.impl.inmemory;

import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportQuery;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryReportRepository implements ReportRepository {

    private final Map<Integer, Report> reports = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Optional<Report> findById(Integer id) {
        return Optional.ofNullable(reports.get(id));
    }

    @Override
    public Report save(Report report) {
        if (report.getId() == null) {
            int id = nextId.getAndIncrement();
            Report newReport = new Report(id, report.getDescription(), report.getReporterId(),
                    report.getReportedId(), report.getAuctionId());
            if (report.getResult() != null) {
                newReport.setResult(report.getResult());
            }
            reports.put(id, newReport);
            return newReport;
        }
        reports.put(report.getId(), report);
        return report;
    }

    @Override
    public void deleteById(Integer id) {
        reports.remove(id);
    }

    @Override
    public List<Report> find(ReportQuery query) {
        return reports.values().stream()
                .filter(r -> query.getReporterId() == null || r.getReporterId().equals(query.getReporterId()))
                .filter(r -> query.getReportedId() == null || r.getReportedId().equals(query.getReportedId()))
                .filter(r -> query.getAuctionId() == null || r.getAuctionId().equals(query.getAuctionId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Report> findPaged(ReportQuery query, int page, int size) {
        return find(query).stream()
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
