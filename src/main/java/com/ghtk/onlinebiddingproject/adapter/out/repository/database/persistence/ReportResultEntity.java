package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "result", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportResultStatus result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminEntity admin;

    @OneToOne
    @JoinColumn(name = "report_id", nullable = false)
    private ReportEntity report;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
