package com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryAuctionRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryBidRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryCategoryRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryItemRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryNotificationRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryReportImageRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryReportRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryReportResultRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryUserRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryVerificationTokenRepository;
import com.ghtk.onlinebiddingproject.application.impl.test.inmemory.InMemoryWinnerRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.CategoryRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.WinnerRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportImageRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import org.junit.jupiter.api.Test;

class TestRepositoryFactoryTest {

    @Test
    void shouldProvideAllRepositoryImplementations() {
        AuctionRepository auctionRepo = TestRepositoryFactory.createAuctionRepository();
        BidRepository bidRepo = TestRepositoryFactory.createBidRepository();
        UserRepository userRepo = TestRepositoryFactory.createUserRepository();
        VerificationTokenRepository verificationTokenRepo = TestRepositoryFactory.createVerificationTokenRepository();
        ItemRepository itemRepo = TestRepositoryFactory.createItemRepository();
        NotificationRepository notificationRepo = TestRepositoryFactory.createNotificationRepository();
        ReportRepository reportRepo = TestRepositoryFactory.createReportRepository();
        ReportResultRepository reportResultRepo = TestRepositoryFactory.createReportResultRepository();
        ReportImageRepository reportImageRepo = TestRepositoryFactory.createReportImageRepository();
        CategoryRepository categoryRepo = TestRepositoryFactory.createCategoryRepository();
        WinnerRepository winnerRepo = TestRepositoryFactory.createWinnerRepository();

        assertNotNull(auctionRepo);
        assertNotNull(bidRepo);
        assertNotNull(userRepo);
        assertNotNull(verificationTokenRepo);
        assertNotNull(itemRepo);
        assertNotNull(notificationRepo);
        assertNotNull(reportRepo);
        assertNotNull(reportResultRepo);
        assertNotNull(reportImageRepo);
        assertNotNull(categoryRepo);
        assertNotNull(winnerRepo);

        assertInstanceOf(InMemoryAuctionRepository.class, auctionRepo);
        assertInstanceOf(InMemoryBidRepository.class, bidRepo);
        assertInstanceOf(InMemoryUserRepository.class, userRepo);
        assertInstanceOf(InMemoryVerificationTokenRepository.class, verificationTokenRepo);
        assertInstanceOf(InMemoryItemRepository.class, itemRepo);
        assertInstanceOf(InMemoryNotificationRepository.class, notificationRepo);
        assertInstanceOf(InMemoryReportRepository.class, reportRepo);
        assertInstanceOf(InMemoryReportResultRepository.class, reportResultRepo);
        assertInstanceOf(InMemoryReportImageRepository.class, reportImageRepo);
        assertInstanceOf(InMemoryCategoryRepository.class, categoryRepo);
        assertInstanceOf(InMemoryWinnerRepository.class, winnerRepo);
    }

    @Test
    void shouldReturnFreshInstances() {
        AuctionRepository repo1 = TestRepositoryFactory.createAuctionRepository();
        AuctionRepository repo2 = TestRepositoryFactory.createAuctionRepository();
        assertNotSame(repo1, repo2);
    }
}
