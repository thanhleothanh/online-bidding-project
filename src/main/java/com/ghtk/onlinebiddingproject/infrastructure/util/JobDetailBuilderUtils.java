package com.ghtk.onlinebiddingproject.infrastructure.util;

import com.ghtk.onlinebiddingproject.infrastructure.quartz.CancelAuctionJob;
import com.ghtk.onlinebiddingproject.infrastructure.quartz.EndAuctionJob;
import com.ghtk.onlinebiddingproject.infrastructure.quartz.StartAuctionJob;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JobDetailBuilderUtils {

    public static JobDetail buildStartAuctionJobDetail(JobDataMap jobDataMap) {
        Integer auctionId = jobDataMap.getInt("auctionId");
        String jobDetailName = getStartAuctionJobDetailName(auctionId);
        String jobDetailGroup = getStartAuctionJobDetailGroup();
        String jobDetailDescription = getStartAuctionJobDetailDescription(auctionId);

        return JobBuilder.newJob(StartAuctionJob.class)
                .withIdentity(jobDetailName, jobDetailGroup)
                .withDescription(jobDetailDescription)
                .usingJobData(jobDataMap)
                .storeDurably(false)
                .build();
    }

    public static JobDetail buildCancelAuctionJobDetail(JobDataMap jobDataMap) {
        Integer auctionId = jobDataMap.getInt("auctionId");
        String jobDetailName = getCancelAuctionJobDetailName(auctionId);
        String jobDetailGroup = getCancelAuctionJobDetailGroup();
        String jobDetailDescription = getCancelAuctionJobDetailDescription(auctionId);

        return JobBuilder.newJob(CancelAuctionJob.class)
                .withIdentity(jobDetailName, jobDetailGroup)
                .withDescription(jobDetailDescription)
                .usingJobData(jobDataMap)
                .storeDurably(false)
                .build();
    }

    public static JobDetail buildEndAuctionJobDetail(JobDataMap jobDataMap) {
        Integer auctionId = jobDataMap.getInt("auctionId");
        String jobDetailName = getEndAuctionJobDetailName(auctionId);
        String jobDetailGroup = getEndAuctionJobDetailGroup();
        String jobDetailDescription = getEndAuctionJobDetailDescription(auctionId);

        return JobBuilder.newJob(EndAuctionJob.class)
                .withIdentity(jobDetailName, jobDetailGroup)
                .withDescription(jobDetailDescription)
                .usingJobData(jobDataMap)
                .storeDurably(false)
                .build();
    }

    public static Trigger buildJobTrigger(JobDetail jobDetail, LocalDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), jobDetail.getKey().getGroup())
                .withDescription(jobDetail.getDescription())
                .startAt(Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

    public static String getStartAuctionJobDetailGroup() {
        return "START AUCTIONS";
    }

    public static String getCancelAuctionJobDetailGroup() {
        return "CANCEL AUCTIONS";
    }

    public static String getEndAuctionJobDetailGroup() {
        return "END AUCTIONS";
    }

    public static String getStartAuctionJobDetailDescription(Integer auctionId) {
        return "SCHEDULING OPENING AUCTION #" + auctionId;
    }

    public static String getCancelAuctionJobDetailDescription(Integer auctionId) {
        return "SCHEDULING CANCELLATION AUCTION #" + auctionId;
    }

    public static String getEndAuctionJobDetailDescription(Integer auctionId) {
        return "SCHEDULING ENDING AUCTION #" + auctionId;
    }

    public static String getStartAuctionJobDetailName(Integer auctionId) {
        return "START" + auctionId;
    }

    public static String getCancelAuctionJobDetailName(Integer auctionId) {
        return "CANCEL" + auctionId;
    }

    public static String getEndAuctionJobDetailName(Integer auctionId) {
        return "END" + auctionId;
    }
}
