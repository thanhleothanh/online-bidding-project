package com.ghtk.onlinebiddingproject.application.impl.test.inmemory.factories;

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

public final class TestRepositoryFactory {

    private TestRepositoryFactory() {}

    public static AuctionRepository createAuctionRepository() {
        return new InMemoryAuctionRepository();
    }

    public static BidRepository createBidRepository() {
        return new InMemoryBidRepository();
    }

    public static UserRepository createUserRepository() {
        return new InMemoryUserRepository();
    }

    public static VerificationTokenRepository createVerificationTokenRepository() {
        return new InMemoryVerificationTokenRepository();
    }

    public static ItemRepository createItemRepository() {
        return new InMemoryItemRepository();
    }

    public static NotificationRepository createNotificationRepository() {
        return new InMemoryNotificationRepository();
    }

    public static ReportRepository createReportRepository() {
        return new InMemoryReportRepository();
    }

    public static ReportResultRepository createReportResultRepository() {
        return new InMemoryReportResultRepository();
    }

    public static ReportImageRepository createReportImageRepository() {
        return new InMemoryReportImageRepository();
    }

    public static CategoryRepository createCategoryRepository() {
        return new InMemoryCategoryRepository();
    }

    public static WinnerRepository createWinnerRepository() {
        return new InMemoryWinnerRepository();
    }
}
