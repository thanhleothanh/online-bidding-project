package com.ghtk.onlinebiddingproject.configurations;

import com.ghtk.onlinebiddingproject.application.*;
import com.ghtk.onlinebiddingproject.application.impl.*;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.*;
import com.ghtk.onlinebiddingproject.domain.model.item.ImageStoragePort;
import com.ghtk.onlinebiddingproject.domain.model.item.ItemRepository;
import com.ghtk.onlinebiddingproject.domain.model.notification.NotificationRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportRepository;
import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.UserRepository;
import com.ghtk.onlinebiddingproject.domain.model.auth.VerificationTokenRepository;
import com.ghtk.onlinebiddingproject.application.port.JwtPort;
import com.ghtk.onlinebiddingproject.application.port.MailPort;
import com.ghtk.onlinebiddingproject.domain.port.WebSocketPort;
import com.ghtk.onlinebiddingproject.domain.service.AuctionLifecycleService;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {

    @Bean
    public AuthService authService(UserRepository userRepository,
                                   VerificationTokenRepository verificationTokenRepository,
                                   MailPort mailPort,
                                   JwtPort jwtPort,
                                   EventPublisher eventPublisher) {
        return new AuthServiceImpl(userRepository, verificationTokenRepository, mailPort, jwtPort, eventPublisher);
    }

    @Bean
    public AuctionService auctionService(AuctionRepository auctionRepository,
                                         BidRepository bidRepository,
                                         WinnerRepository winnerRepository,
                                         EventPublisher eventPublisher) {
        return new AuctionServiceImpl(auctionRepository, bidRepository, winnerRepository, eventPublisher);
    }

    @Bean
    public BidService bidService(BidRepository bidRepository,
                                 AuctionRepository auctionRepository,
                                 EventPublisher eventPublisher) {
        return new BidServiceImpl(bidRepository, auctionRepository, eventPublisher);
    }

    @Bean
    public ItemService itemService(ItemRepository itemRepository,
                                   ImageStoragePort imageStoragePort) {
        return new ItemServiceImpl(itemRepository, imageStoragePort);
    }

    @Bean
    public ReportService reportService(ReportRepository reportRepository,
                                       ReportResultRepository reportResultRepository,
                                       UserRepository userRepository) {
        return new ReportServiceImpl(reportRepository, reportResultRepository, userRepository);
    }

    @Bean
    public NotificationService notificationService(NotificationRepository notificationRepository,
                                                   WebSocketPort webSocketPort) {
        return new NotificationServiceImpl(notificationRepository, webSocketPort);
    }

    @Bean
    public CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public AuctionLifecycleService auctionLifecycleService(AuctionRepository auctionRepository,
                                                          BidRepository bidRepository,
                                                          WinnerRepository winnerRepository) {
        return new AuctionLifecycleService(auctionRepository, bidRepository, winnerRepository);
    }

    @Bean
    public JobSchedulerService jobSchedulerService(Scheduler scheduler) {
        return new JobSchedulerServiceImpl(scheduler);
    }
}
