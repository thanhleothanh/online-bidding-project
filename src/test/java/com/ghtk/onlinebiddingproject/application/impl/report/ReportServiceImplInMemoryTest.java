package com.ghtk.onlinebiddingproject.application.impl.report;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories.TestRepositoryFactory;
import com.ghtk.onlinebiddingproject.domain.model.auth.Profile;
import com.ghtk.onlinebiddingproject.domain.model.auth.Role;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserStatus;

class ReportServiceImplInMemoryTest extends AbstractReportServiceTest {

    @Override
    protected void initDependencies() {
        reportRepository = TestRepositoryFactory.createReportRepository();
        reportResultRepository = TestRepositoryFactory.createReportResultRepository();
        userRepository = TestRepositoryFactory.createUserRepository();
    }

    @Override
    protected void setUpData() {
        Profile reporter = new Profile(1, "reporter", "pass", "Reporter", "reporter@test.com", Role.user());
        reporter.setStatus(UserStatus.ACTIVE);
        userRepository.save(reporter);

        Profile reported = new Profile(2, "reported", "pass", "Reported", "reported@test.com", Role.user());
        reported.setStatus(UserStatus.ACTIVE);
        reported.setLegitimateScore(10);
        userRepository.save(reported);
    }
}
