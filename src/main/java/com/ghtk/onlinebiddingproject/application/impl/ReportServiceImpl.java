package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.ReportService;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.*;
import com.ghtk.onlinebiddingproject.domain.service.LegitimateScoreService;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportResultRepository reportResultRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(ReportRepository reportRepository,
                            ReportResultRepository reportResultRepository,
                            UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.reportResultRepository = reportResultRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Report submit(Report report, Integer userId) {
        return reportRepository.save(report);
    }

    @Override
    public Report getById(Integer id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    @Override
    public List<Report> getAll(ReportQuery query, int page, int size) {
        return reportRepository.findPaged(query, page, size);
    }

    @Override
    public Report adminGetById(Integer id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    @Override
    public void adminDelete(Integer id) {
        reportRepository.deleteById(id);
    }

    @Override
    public Report adminJudge(Integer id, ReportResultStatus status, Integer adminId) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        if (report.hasResult()) {
            throw new IllegalStateException("Report already judged");
        }

        ReportResult result = ReportResult.judge(adminId, status);
        result.setReportId(id);
        reportResultRepository.save(result);
        report.setResult(result);

        if (status.isAccepted()) {
            LegitimateScoreService.deductScore(report.getReportedId(), userRepository);
        }

        return reportRepository.save(report);
    }
}
