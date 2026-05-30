package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "winner")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WinnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bid_id", nullable = false)
    private Integer bidId;

    @Column(name = "auction_id", nullable = false)
    private Integer auctionId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;
}
