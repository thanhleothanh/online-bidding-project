package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_report")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "notification_id", nullable = false)
    private Integer notificationId;

    @Column(name = "report_id", nullable = false)
    private Integer reportId;
}
