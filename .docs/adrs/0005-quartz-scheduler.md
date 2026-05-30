# ADR-0005: Quartz Scheduler for Auction Automation

## Status

Accepted

## Context

Auctions require automatic state transitions:

- When a user submits an auction and it is approved, the auction must automatically start at `timeStart` and end at `timeEnd`
- If an auction is not approved by `timeStart`, it should be automatically cancelled
- These transitions must be durable (survive server restarts) and precise

In-process solutions like `@Scheduled` or `ScheduledExecutorService` do not survive restarts and are not suitable for scheduling future jobs that may be created hours or days in advance.

## Decision

Use Quartz Scheduler with JDBC job store:

1. Add `spring-boot-starter-quartz` dependency
2. Configure `spring.quartz.job-store-type=jdbc` in `application.properties`
3. Use MySQL-compatible schema: `spring.quartz.jdbc.schema=classpath:org/quartz/impl/jdbcjobstore/tables_mysql_innodb.sql`
4. Create three job classes extending `QuartzJobBean`:
   - `StartAuctionJob` — Sets auction status to OPENING, creates start notifications
   - `EndAuctionJob` — Sets auction status to ENDED, determines winner, creates end notifications
   - `CancelAuctionJob` — Sets auction status to CANCELED
5. Use `JobSchedulerServiceImpl` to schedule/cancel jobs using `JobDataMap` to pass auction IDs
6. When an admin approves an auction, schedule both `StartAuctionJob` (at `timeStart`) and `EndAuctionJob` (at `timeEnd`)
7. When a user submits an auction, schedule `CancelAuctionJob` (at `timeStart`) as a safety net
8. When the start job fires, delete the pending cancel job

## Consequences

- **Positive**: Auction state transitions survive server restarts
- **Positive**: Jobs are stored in MySQL, providing durability and observability
- **Positive**: Clean separation between scheduling logic and business logic
- **Negative**: Adds Quartz dependency and JDBC schema management
- **Negative**: 5 threads configured (`threadPool.threadCount=5`), which limits concurrent job execution
- **Neutral**: `JobDataMap` passes only auction IDs; job logic fetches full auction state from DB at execution time
