package com.ghtk.onlinebiddingproject.application.impl.report;

import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@Tag("integration-tests")
@Sql(scripts = "classpath:sql/seed-report-full.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReportServiceImplDatabaseTest extends AbstractReportServiceTest {

    @Autowired
    private ReportRepository autReportRepository;

    @Autowired
    private ReportResultRepository autReportResultRepository;

    @Autowired
    private UserRepository autUserRepository;

    @Override
    protected void initDependencies() {
        reportRepository = autReportRepository;
        reportResultRepository = autReportResultRepository;
        userRepository = autUserRepository;
    }

    @Override
    protected void setUpData() {
        // seed-roles.sql already has the required data
    }
}
