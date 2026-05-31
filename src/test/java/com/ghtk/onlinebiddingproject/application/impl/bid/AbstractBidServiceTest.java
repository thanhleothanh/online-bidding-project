package com.ghtk.onlinebiddingproject.application.impl.bid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ghtk.onlinebiddingproject.application.impl.BidServiceImpl;
import com.ghtk.onlinebiddingproject.domain.event.EventPublisher;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionDraft;
import com.ghtk.onlinebiddingproject.domain.model.auction.AuctionRepository;
import com.ghtk.onlinebiddingproject.domain.model.auction.BidRepository;
import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractBidServiceTest {

    protected BidRepository bidRepository;
    protected AuctionRepository auctionRepository;
    protected EventPublisher eventPublisher;
    protected BidServiceImpl bidService;

    @BeforeEach
    void setUp() {
        initDependencies();
        bidService = new BidServiceImpl(bidRepository, auctionRepository, eventPublisher);
    }

    protected abstract void initDependencies();
    protected abstract Auction createOpeningAuction(Integer ownerId);

    @Test
    void shouldRejectBidOnNonOpeningAuction() {
        AuctionDraft draft = new AuctionDraft("Test",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(7),
                1000.0, 100.0, 1, 1);
        Auction auction = Auction.create(draft, 1);
        Auction saved = auctionRepository.save(auction);

        assertThrows(IllegalStateException.class,
                () -> bidService.placeBid(saved.getId(), Money.of(1500.0), 2));
    }

    @Test
    void shouldRejectBidFromOwner() {
        Auction auction = createOpeningAuction(1);

        assertThrows(IllegalStateException.class,
                () -> bidService.placeBid(auction.getId(), Money.of(1500.0), 1));
    }

    @Test
    void shouldRejectBidBelowStartPrice() {
        Auction auction = createOpeningAuction(1);

        assertThrows(IllegalArgumentException.class,
                () -> bidService.placeBid(auction.getId(), Money.of(500.0), 2));
    }

    @Test
    void shouldGetBidsByAuctionId() {
        Auction auction = createOpeningAuction(1);
        bidService.placeBid(auction.getId(), Money.of(1500.0), 2);
        bidService.placeBid(auction.getId(), Money.of(2000.0), 3);

        var bids = bidService.getByAuctionId(auction.getId());

        assertEquals(2, bids.size());
    }

    @Test
    void shouldRejectBidBelowPriceStep() {
        Auction auction = createOpeningAuction(1);
        bidService.placeBid(auction.getId(), Money.of(1500.0), 2);

        assertThrows(IllegalArgumentException.class,
                () -> bidService.placeBid(auction.getId(), Money.of(1550.0), 3));
    }

    @Test
    void shouldGetHighestBid() {
        Auction auction = createOpeningAuction(1);
        bidService.placeBid(auction.getId(), Money.of(1500.0), 2);
        bidService.placeBid(auction.getId(), Money.of(2000.0), 3);

        var bids = bidService.getByAuctionId(auction.getId());

        assertEquals(2, bids.size());
        assertEquals(Money.of(2000.0), bids.get(1).getPrice());
    }
}
