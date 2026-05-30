package com.ghtk.onlinebiddingproject.domain.model.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportTest {

    @Test
    void shouldCreateReport() {
        Report report = Report.create(1, 2, 10, "Spam content");

        assertNull(report.getId());
        assertEquals("Spam content", report.getDescription());
        assertEquals(1, report.getReporterId());
        assertEquals(2, report.getReportedId());
        assertEquals(10, report.getAuctionId());
    }

    @Test
    void shouldRejectSelfReport() {
        assertThrows(IllegalArgumentException.class,
                () -> Report.create(1, 1, 10, "Description"));
    }

    @Test
    void shouldRejectBlankDescription() {
        assertThrows(IllegalArgumentException.class,
                () -> Report.create(1, 2, 10, "  "));
    }

    @Test
    void shouldAlwaysAllowJudging() {
        Report report = Report.create(1, 2, 10, "Description");
        assertTrue(report.canBeJudgedBy(99));
    }

    @Test
    void shouldTrackResult() {
        Report report = Report.create(1, 2, 10, "Description");
        assertFalse(report.hasResult());

        ReportResult result = ReportResult.judge(3, ReportResultStatus.ACCEPTED);
        report.setResult(result);
        assertTrue(report.hasResult());
    }

    @Test
    void shouldAddImage() {
        Report report = Report.create(1, 2, 10, "Description");
        ReportImage image = new ReportImage(1, "https://example.com/img.jpg", "pub123");

        report.addImage(image);

        assertEquals(1, report.getImages().size());
    }
}
