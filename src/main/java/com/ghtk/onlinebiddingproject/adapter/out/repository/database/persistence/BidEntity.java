package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "auction_id", nullable = false)
    private Integer auctionId;

    @Column(name = "bid_time", nullable = false)
    private LocalDateTime bidTime;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
