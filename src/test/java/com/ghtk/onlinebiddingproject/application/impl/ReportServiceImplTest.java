package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryReportRepository;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryReportResultRepository;
import com.ghtk.onlinebiddingproject.application.impl.inmemory.InMemoryUserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.report.Report;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceImplTest {

    private ReportServiceImpl reportService;
    private InMemoryReportRepository reportRepository;
    private InMemoryReportResultRepository reportResultRepository;
    private InMemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        reportRepository = new InMemoryReportRepository();
        reportResultRepository = new InMemoryReportResultRepository();
        userRepository = new InMemoryUserRepository();

        reportService = new ReportServiceImpl(reportRepository, reportResultRepository, userRepository);

        Profile reporter = new Profile(1, "reporter", "pass", "Reporter", "reporter@test.com", Role.user());
        reporter.setStatus(UserStatus.ACTIVE);
        userRepository.save(reporter);

        Profile reported = new Profile(2, "reported", "pass", "Reported", "reported@test.com", Role.user());
        reported.setStatus(UserStatus.ACTIVE);
        reported.setLegitimateScore(10);
        userRepository.save(reported);
    }

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
