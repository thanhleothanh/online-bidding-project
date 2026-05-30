package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;

import java.time.LocalDateTime;
import java.util.Objects;

public class Auction {

    private Integer id;
    private String description;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Money priceStart;
    private Money priceStep;
    private Money highestPrice;
    private AuctionStatus status;
    private Integer ownerId;
    private Integer categoryId;
    private Integer itemId;

    public Auction(Integer id, String description, LocalDateTime timeStart, LocalDateTime timeEnd,
                   Money priceStart, Money priceStep, AuctionStatus status,
                   Integer ownerId, Integer categoryId, Integer itemId) {
        this.id = id;
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priceStart = priceStart;
        this.priceStep = priceStep;
        this.highestPrice = priceStart;
        this.status = status;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
        this.itemId = itemId;
    }

    public static Auction create(AuctionDraft draft, Integer ownerId) {
        if (draft.getTimeStart().isAfter(draft.getTimeEnd())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        if (draft.getTimeStart().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Start time cannot be in the past");
        }
        if (draft.getPriceStart() <= 0) {
            throw new IllegalArgumentException("Start price must be positive");
        }
        if (draft.getPriceStep() <= 0) {
            throw new IllegalArgumentException("Price step must be positive");
        }
        return new Auction(null, draft.getDescription(), draft.getTimeStart(), draft.getTimeEnd(),
                Money.of(draft.getPriceStart()), Money.of(draft.getPriceStep()),
                AuctionStatus.DRAFT, ownerId, draft.getCategoryId(), draft.getItemId());
    }

    public void submitForReview() {
        if (this.status != AuctionStatus.DRAFT) {
            throw new IllegalStateException("Can only submit draft auctions for review");
        }
        if (this.itemId == null) {
            throw new IllegalStateException("Auction must have an item to submit for review");
        }
        this.status = AuctionStatus.PENDING;
    }

    public void approve(Integer adminId) {
        if (this.status != AuctionStatus.PENDING) {
            throw new IllegalStateException("Can only approve pending auctions");
        }
        this.status = AuctionStatus.QUEUED;
    }

    public void reject(Integer adminId) {
        if (this.status != AuctionStatus.PENDING) {
            throw new IllegalStateException("Can only reject pending auctions");
        }
        this.status = AuctionStatus.CANCELED;
    }

    public void start() {
        if (this.status != AuctionStatus.QUEUED) {
            throw new IllegalStateException("Can only start queued auctions");
        }
        this.status = AuctionStatus.OPENING;
    }

    public void end() {
        if (this.status != AuctionStatus.OPENING) {
            throw new IllegalStateException("Can only end opening auctions");
        }
        this.status = AuctionStatus.ENDED;
    }

    public void cancel() {
        if (this.status == AuctionStatus.ENDED || this.status == AuctionStatus.CANCELED) {
            throw new IllegalStateException("Cannot cancel ended or canceled auctions");
        }
        this.status = AuctionStatus.CANCELED;
    }

    public void placeBid(Money amount) {
        if (!this.status.canBeBiddedOn()) {
            throw new IllegalStateException("Can only bid on opening auctions");
        }
        if (amount.isLessThan(this.priceStart)) {
            throw new IllegalArgumentException("Bid must be at least the starting price");
        }
        if (amount.isLessThan(this.highestPrice.add(this.priceStep))) {
            throw new IllegalArgumentException("Bid must be at least highest price + step");
        }
        this.highestPrice = amount;
    }

    public boolean canBeEditedBy(Integer userId) {
        return isOwnedBy(userId) && (this.status == AuctionStatus.DRAFT || this.status == AuctionStatus.PENDING);
    }

    public boolean canBeDeletedBy(Integer userId) {
        return isOwnedBy(userId) && !this.status.isTerminal();
    }

    public boolean isOwnedBy(Integer userId) {
        return this.ownerId.equals(userId);
    }

    public boolean hasItem() {
        return this.itemId != null;
    }

    public Integer getId() { return id; }
    public String getDescription() { return description; }
    public LocalDateTime getTimeStart() { return timeStart; }
    public LocalDateTime getTimeEnd() { return timeEnd; }
    public Money getPriceStart() { return priceStart; }
    public Money getPriceStep() { return priceStep; }
    public Money getHighestPrice() { return highestPrice; }
    public AuctionStatus getStatus() { return status; }
    public Integer getOwnerId() { return ownerId; }
    public Integer getCategoryId() { return categoryId; }
    public Integer getItemId() { return itemId; }

    public void setHighestPrice(Money highestPrice) { this.highestPrice = highestPrice; }

    public void setDescription(String description) { this.description = description; }
    public void setTimeStart(LocalDateTime timeStart) { this.timeStart = timeStart; }
    public void setTimeEnd(LocalDateTime timeEnd) { this.timeEnd = timeEnd; }
    public void setStatus(AuctionStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return Objects.equals(id, auction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
