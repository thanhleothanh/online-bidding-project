package com.ghtk.onlinebiddingproject.repositories;

import com.ghtk.onlinebiddingproject.models.entities.NotificationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationReportRepository extends JpaRepository<NotificationReport, Integer> {

    List<NotificationReport> findByReport_Id(Integer id);
}
