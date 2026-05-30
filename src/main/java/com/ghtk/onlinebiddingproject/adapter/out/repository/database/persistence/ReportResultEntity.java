package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import com.ghtk.onlinebiddingproject.domain.model.report.ReportResultStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportResultStatus status;

    @Column(name = "admin_id", nullable = false)
    private Integer adminId;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;
}
