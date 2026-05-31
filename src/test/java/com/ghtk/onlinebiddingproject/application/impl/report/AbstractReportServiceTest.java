package com.ghtk.onlinebiddingproject.application.impl.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ghtk.onlinebiddingproject.application.impl.ReportServiceImpl;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractReportServiceTest {

    protected ReportRepository reportRepository;
    protected ReportResultRepository reportResultRepository;
    protected UserRepository userRepository;
    protected ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        initDependencies();
        reportService = new ReportServiceImpl(reportRepository, reportResultRepository, userRepository);
        setUpData();
    }

    protected abstract void initDependencies();
    protected abstract void setUpData();

    @Test
    void shouldSubmitReport() {
        Report report = Report.create(1, 2, 10, "Spam content");

        Report submitted = reportService.submit(report, 1);

        assertNotNull(submitted.getId());
        assertEquals("Spam content", submitted.getDescription());
    }

    @Test
    void shouldGetReportById() {
        Report report = Report.create(1, 2, 10, "Spam content");
        Report saved = reportService.submit(report, 1);

        Report found = reportService.getById(saved.getId());

        assertEquals(saved.getId(), found.getId());
    }

    @Test
    void shouldAdminJudgeAccept() {
        Report report = Report.create(1, 2, 10, "Spam content");
        Report saved = reportService.submit(report, 1);

        Report judged = reportService.adminJudge(saved.getId(), ReportResultStatus.ACCEPTED, 3);

        assertTrue(judged.hasResult());
        assertTrue(judged.getResult().isAccepted());

        Profile reported = userRepository.findById(2).orElseThrow();
        assertEquals(5, reported.getLegitimateScore());
    }

    @Test
    void shouldAdminJudgeReject() {
        Report report = Report.create(1, 2, 10, "Spam content");
        Report saved = reportService.submit(report, 1);

        Report judged = reportService.adminJudge(saved.getId(), ReportResultStatus.REJECTED, 3);

        assertTrue(judged.hasResult());
        assertTrue(judged.getResult().isRejected());

        Profile reported = userRepository.findById(2).orElseThrow();
        assertEquals(10, reported.getLegitimateScore());
    }

    @Test
    void shouldRejectAlreadyJudgedReport() {
        Report report = Report.create(1, 2, 10, "Spam content");
        Report saved = reportService.submit(report, 1);
        reportService.adminJudge(saved.getId(), ReportResultStatus.ACCEPTED, 3);

        assertThrows(IllegalStateException.class,
                () -> reportService.adminJudge(saved.getId(), ReportResultStatus.REJECTED, 3));
    }
}
