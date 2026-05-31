package com.ghtk.onlinebiddingproject.application.impl;

import com.ghtk.onlinebiddingproject.application.JobSchedulerService;
import com.ghtk.onlinebiddingproject.domain.model.auction.Auction;
import com.ghtk.onlinebiddingproject.infrastructure.util.JobDetailBuilderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobSchedulerServiceImpl implements JobSchedulerService {

    private final Scheduler scheduler;

    @Override
    public void startAuctionScheduler(Auction auction) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("auctionId", auction.getId());

            JobDetail jobDetail = JobDetailBuilderUtils.buildStartAuctionJobDetail(jobDataMap);
            Trigger trigger = JobDetailBuilderUtils.buildJobTrigger(jobDetail, auction.getTimeStart());

            scheduler.scheduleJob(jobDetail, trigger);

            // Delete the pending cancel job if it exists
            boolean deleted = scheduler.deleteJob(new JobKey(
                    JobDetailBuilderUtils.getCancelAuctionJobDetailName(auction.getId()),
                    JobDetailBuilderUtils.getCancelAuctionJobDetailGroup()
            ));
            log.info("Deleted scheduled cancellation job: {}", deleted);
        } catch (SchedulerException e) {
            log.error("Error scheduling start auction job: {}", e.getMessage());
        }
    }

    @Override
    public void endAuctionScheduler(Auction auction) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("auctionId", auction.getId());

            JobDetail jobDetail = JobDetailBuilderUtils.buildEndAuctionJobDetail(jobDataMap);
            Trigger trigger = JobDetailBuilderUtils.buildJobTrigger(jobDetail, auction.getTimeEnd());

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Error scheduling end auction job: {}", e.getMessage());
        }
    }

    @Override
    public void cancelAuctionScheduler(Auction auction) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("auctionId", auction.getId());

            JobDetail jobDetail = JobDetailBuilderUtils.buildCancelAuctionJobDetail(jobDataMap);
            Trigger trigger = JobDetailBuilderUtils.buildJobTrigger(jobDetail, auction.getTimeStart());

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("Error scheduling cancel auction job: {}", e.getMessage());
        }
    }
}
