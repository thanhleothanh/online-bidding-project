package com.ghtk.onlinebiddingproject.application.impl.report;

import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import com.ghtk.onlinebiddingproject.infrastructure.IntegrationDatabaseTest;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationDatabaseTest
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
        Profile reporter = new Profile(1, "reporter", "pass", "Reporter", "reporter@test.com", Role.user());
        reporter.setStatus(UserStatus.ACTIVE);
        userRepository.save(reporter);
        userRepository.insertUser(1);

        Profile reported = new Profile(2, "reported", "pass", "Reported", "reported@test.com", Role.user());
        reported.setStatus(UserStatus.ACTIVE);
        reported.setLegitimateScore(10);
        userRepository.save(reported);
        userRepository.insertUser(2);
    }
}
