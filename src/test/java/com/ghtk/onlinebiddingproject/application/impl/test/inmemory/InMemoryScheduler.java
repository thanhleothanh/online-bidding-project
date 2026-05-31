package com.ghtk.onlinebiddingproject.application.impl.test.inmemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

public class InMemoryScheduler implements Scheduler {

    private final Map<JobKey, JobDetail> jobs = new ConcurrentHashMap<>();
    private final Map<JobKey, List<Trigger>> triggersByJob = new ConcurrentHashMap<>();
    private boolean started = false;
    private boolean shutdown = false;

    @Override
    public String getSchedulerName() {
        return "InMemoryScheduler";
    }

    @Override
    public String getSchedulerInstanceId() {
        return "InMemoryScheduler-Instance";
    }

    @Override
    public SchedulerContext getContext() {
        return null;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void startDelayed(int seconds) {
        start();
    }

    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void standby() {
    }

    @Override
    public boolean isInStandbyMode() {
        return false;
    }

    @Override
    public void shutdown() {
        shutdown(true);
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) {
        shutdown = true;
        jobs.clear();
        triggersByJob.clear();
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }

    @Override
    public SchedulerMetaData getMetaData() {
        return null;
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() {
        return Collections.emptyList();
    }

    @Override
    public void setJobFactory(JobFactory jobFactory) {
    }

    @Override
    public ListenerManager getListenerManager() {
        return null;
    }

    @Override
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) {
        jobs.put(jobDetail.getKey(), jobDetail);
        triggersByJob.computeIfAbsent(jobDetail.getKey(), k -> new ArrayList<>()).add(trigger);
        return trigger.getNextFireTime();
    }

    @Override
    public Date scheduleJob(Trigger trigger) {
        return trigger.getNextFireTime();
    }

    @Override
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) {
        for (Map.Entry<JobDetail, Set<? extends Trigger>> entry : triggersAndJobs.entrySet()) {
            addJob(entry.getKey(), replace);
            for (Trigger trigger : entry.getValue()) {
                scheduleJob(entry.getKey(), trigger);
            }
        }
    }

    @Override
    public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggers, boolean replace) {
        addJob(jobDetail, replace);
        for (Trigger trigger : triggers) {
            scheduleJob(jobDetail, trigger);
        }
    }

    @Override
    public boolean unscheduleJob(TriggerKey triggerKey) {
        return false;
    }

    @Override
    public boolean unscheduleJobs(List<TriggerKey> triggerKeys) {
        return false;
    }

    @Override
    public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) {
        return null;
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace) {
        if (replace) {
            jobs.put(jobDetail.getKey(), jobDetail);
        } else {
            jobs.putIfAbsent(jobDetail.getKey(), jobDetail);
        }
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) {
        addJob(jobDetail, replace);
    }

    @Override
    public boolean deleteJob(JobKey jobKey) {
        boolean existed = jobs.remove(jobKey) != null;
        triggersByJob.remove(jobKey);
        return existed;
    }

    @Override
    public boolean deleteJobs(List<JobKey> jobKeys) {
        boolean allDeleted = true;
        for (JobKey key : jobKeys) {
            if (!deleteJob(key)) {
                allDeleted = false;
            }
        }
        return allDeleted;
    }

    @Override
    public void triggerJob(JobKey jobKey) {
    }

    @Override
    public void triggerJob(JobKey jobKey, JobDataMap jobDataMap) {
    }

    @Override
    public void pauseJob(JobKey jobKey) {
    }

    @Override
    public void pauseJobs(GroupMatcher<JobKey> groupMatcher) {
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) {
    }

    @Override
    public void pauseTriggers(GroupMatcher<TriggerKey> groupMatcher) {
    }

    @Override
    public void resumeJob(JobKey jobKey) {
    }

    @Override
    public void resumeJobs(GroupMatcher<JobKey> groupMatcher) {
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) {
    }

    @Override
    public void resumeTriggers(GroupMatcher<TriggerKey> groupMatcher) {
    }

    @Override
    public void pauseAll() {
    }

    @Override
    public void resumeAll() {
    }

    @Override
    public List<String> getJobGroupNames() {
        return Collections.emptyList();
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> groupMatcher) {
        return jobs.keySet();
    }

    @Override
    public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) {
        return triggersByJob.getOrDefault(jobKey, Collections.emptyList());
    }

    @Override
    public List<String> getTriggerGroupNames() {
        return Collections.emptyList();
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> groupMatcher) {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getPausedTriggerGroups() {
        return Collections.emptySet();
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey) {
        return jobs.get(jobKey);
    }

    @Override
    public Trigger getTrigger(TriggerKey triggerKey) {
        return null;
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) {
        return Trigger.TriggerState.NONE;
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) {
    }

    @Override
    public void addCalendar(String s, org.quartz.Calendar calendar, boolean b, boolean b1) {
    }

    @Override
    public boolean deleteCalendar(String s) {
        return false;
    }

    @Override
    public org.quartz.Calendar getCalendar(String s) {
        return null;
    }

    @Override
    public List<String> getCalendarNames() {
        return Collections.emptyList();
    }

    @Override
    public boolean interrupt(JobKey jobKey) {
        return false;
    }

    @Override
    public boolean interrupt(String s) {
        return false;
    }

    @Override
    public boolean checkExists(JobKey jobKey) {
        return jobs.containsKey(jobKey);
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) {
        return false;
    }

    @Override
    public void clear() {
        jobs.clear();
        triggersByJob.clear();
    }

    public List<JobDetail> getScheduledJobs() {
        return new ArrayList<>(jobs.values());
    }

    public Map<JobKey, List<Trigger>> getScheduledTriggers() {
        return new HashMap<>(triggersByJob);
    }
}
