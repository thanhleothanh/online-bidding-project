package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private com.ghtk.onlinebiddingproject.constants.NotificationTypeConstants type;

    @Column(nullable = false)
    private String message;

    @Column(name = "auction_id")
    private Integer auctionId;

    @Column(name = "report_id")
    private Integer reportId;
}
