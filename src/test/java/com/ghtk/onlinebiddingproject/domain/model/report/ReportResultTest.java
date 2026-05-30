package com.ghtk.onlinebiddingproject.domain.model.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportResultTest {

    @Test
    void shouldCreateAcceptedResult() {
        ReportResult result = ReportResult.judge(1, ReportResultStatus.ACCEPTED);

        assertTrue(result.isAccepted());
        assertFalse(result.isRejected());
        assertEquals(1, result.getAdminId());
    }

    @Test
    void shouldCreateRejectedResult() {
        ReportResult result = ReportResult.judge(1, ReportResultStatus.REJECTED);

        assertTrue(result.isRejected());
        assertFalse(result.isAccepted());
    }
}
