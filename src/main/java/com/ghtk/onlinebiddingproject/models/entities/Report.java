package com.ghtk.onlinebiddingproject.models.entities;


import com.ghtk.onlinebiddingproject.constants.ReportStatusConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "report")
public class Report extends BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private ReportStatusConstants status;

    @ManyToOne
    @JoinColumn(name = "user_reporter_id", referencedColumnName = "profile_id", nullable = false)
    private User userReporter;

    @ManyToOne
    @JoinColumn(name = "user_reported_id", referencedColumnName = "profile_id", nullable = false)
    private User userReported;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = true)
    private Auction auction;

    @PrePersist
    public void prePersist() {
        this.status = ReportStatusConstants.valueOf("PENDING");
    }
}
