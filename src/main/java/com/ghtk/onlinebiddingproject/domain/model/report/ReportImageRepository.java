package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Optional;

public interface ReportImageRepository {
    ReportImage save(ReportImage image);
    Optional<ReportImage> findById(Integer id);
    void deleteById(Integer id);
}
