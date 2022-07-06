package com.ghtk.onlinebiddingproject.models.entities;


import com.ghtk.onlinebiddingproject.constants.AuctionStatusConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "auction")
public class Auction extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "time_start", nullable = false)
    private LocalDateTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalDateTime timeEnd;

    @Column(name = "price_start", nullable = true)
    private Double priceStart;

    @Column(name = "price_step", nullable = true)
    private Double priceStep;

    @Column(name = "highest_price", nullable = true)
    private Double highestPrice;

    @Column(name = "status", nullable = false)
    private AuctionStatusConstants status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = true)
    private Item item;

    @OneToOne
    @JoinColumn(name = "winner_id", nullable = true)
    private Winner winner;

    @PrePersist
    public void prePersist() {
        this.status = AuctionStatusConstants.valueOf("DRAFT");
    }
}
