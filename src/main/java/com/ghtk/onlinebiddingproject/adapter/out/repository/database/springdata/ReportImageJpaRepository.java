package com.ghtk.onlinebiddingproject.adapter.out.repository.database.springdata;

import com.ghtk.onlinebiddingproject.adapter.out.repository.database.persistence.ReportImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportImageJpaRepository extends JpaRepository<ReportImageEntity, Integer> {
}
