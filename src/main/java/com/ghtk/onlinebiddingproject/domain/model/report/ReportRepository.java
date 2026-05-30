package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository {
    Optional<Report> findById(Integer id);
    Report save(Report report);
    void deleteById(Integer id);
    List<Report> find(ReportQuery query);
    List<Report> findPaged(ReportQuery query, int page, int size);
}
