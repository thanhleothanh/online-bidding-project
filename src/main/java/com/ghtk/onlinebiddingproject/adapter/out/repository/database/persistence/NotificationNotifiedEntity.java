package com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification_notified")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationNotifiedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "notification_id", nullable = false)
    private Integer notificationId;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
}
