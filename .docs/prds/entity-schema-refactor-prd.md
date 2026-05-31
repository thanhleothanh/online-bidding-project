# PRD: Entity-Schema Alignment Refactoring

## Problem Statement

The JPA persistence entities do not match the MySQL database schema. This causes:

1. **Phantom columns** — Entities have fields (`bid_time`, `message`, `userId` on Winner, etc.) that do not exist in the SQL schema, leading to runtime errors when Hibernate attempts to generate SQL for these unmapped columns.
2. **Missing columns** — Entities are missing fields that exist in SQL (`created_at`/`updated_at` on Item and Notification, `profile_id` and `entity_type` on Notification), causing data loss on persist.
3. **Incorrect relationships** — Foreign keys are mapped as bare `Integer` fields instead of JPA `@ManyToOne`/`@OneToMany` relationships, and some `@OneToOne` joins are backwards (ReportEntity→ReportResultEntity).
4. **Type mismatches** — Status/type columns use `@Enumerated(EnumType.ORDINAL)` but the SQL schema stores them as `INT`, while the intended design uses `VARCHAR` with string enum names.
5. **Schema drift** — The SQL seed data uses integer status values (e.g., `profile.status = 2`) while the Java enums use string names (`ACTIVE`), creating a mapping gap.

These mismatches make the persistence layer unreliable, prevent proper use of JPA relationships, and create friction between the domain model and the database.

## Solution

Refactor all 18 JPA entities to exactly match the MySQL schema, convert bare FK fields to proper JPA relationships, update the SQL schema to use `VARCHAR` for status/type columns, and update all mappers to handle the new entity structure. Domain models remain flat (IDs only) to serve as API DTOs.

## User Stories

1. As a developer, I want JPA entities that exactly match the SQL schema, so that Hibernate generates correct SQL without runtime mapping errors.
2. As a developer, I want all foreign keys mapped as `@ManyToOne`/`@OneToMany`/`@OneToOne` relationships, so that JPA can lazily load related data and I can use fetch joins for performance.
3. As a developer, I want status and type columns stored as `VARCHAR` with string enum names in the database, so that the data is human-readable and debuggable without enum ordinal lookup tables.
4. As a developer, I want the `NotificationEntity` to match the SQL schema (with `profile_id`, `notification_type`, `entity_type`, `created_at`, `updated_at`), so that notifications can be correctly persisted and queried.
5. As a developer, I want the `NotificationEntity` to use `@OneToMany` relationships to `notification_auction` and `notification_report` join tables, so that notification-to-entity links are managed through JPA.
6. As a developer, I want the `BidEntity` to use `created_at` instead of the phantom `bid_time` column, so that the entity matches the SQL schema.
7. As a developer, I want the `WinnerEntity` to remove the phantom `user_id` field and derive the winner's user from the associated `Bid`, so that the entity matches the SQL schema.
8. As a developer, I want the `ProfileEntity` email field to be nullable, matching the SQL `DEFAULT NULL`, so that profiles without emails can be persisted.
9. As a developer, I want the `ReportEntity` to have a correct bidirectional `@OneToOne` with `ReportResultEntity` (owned by `ReportResultEntity`), so that the relationship is properly mapped.
10. As a developer, I want the `ItemEntity` to include `created_at` and `updated_at` timestamps, matching the SQL schema.
11. As a developer, I want the `ReportResultEntity` column name to be `result` (matching SQL) instead of `status`, and to use `@Enumerated(EnumType.STRING)`.
12. As a developer, I want a new `ReviewResult` domain model, `ReviewResultStatus` enum, and `ReviewResultRepository` interface, so that admin auction review results have proper domain representation.
13. As a developer, I want a new `ReviewResultMapper` to convert between `ReviewResultEntity` and `ReviewResult` domain model.
14. As a developer, I want a new `AuctionUserEntity` for the `auction_user` join table (no `@ManyToMany`), so that auction watchers are tracked as a standalone entity.
15. As a developer, I want all mappers updated to flatten JPA relationship objects back to IDs in `toDomain()`, so that domain models remain flat and serve as API DTOs.
16. As a developer, I want all `toEntity()` mapper methods to use `EntityManager.getReference()` to create lazy proxies for relationships, so that full objects are not loaded during entity construction.
17. As a developer, I want the SQL seed data updated to use enum string values instead of integers, so that the seed data is consistent with the new `VARCHAR` columns.
18. As a developer, I want the `NotificationMapper` completely rewritten to handle the new entity structure, so that notifications can be correctly converted between entity and domain representations.
19. As a developer, I want the `NotificationAuctionEntity` and `NotificationReportEntity` to have `@ManyToOne` back-references to `NotificationEntity`, so that the bidirectional relationship is properly mapped.
20. As a developer, I want the `ReportImageEntity` to use `@ManyToOne` for the `report_id` FK instead of a bare integer, so that the relationship is managed through JPA.
21. As a developer, I want the `VerificationTokenEntity` to use `@ManyToOne` for the `profile_id` FK, so that the relationship is managed through JPA.
22. As a developer, I want the `ReportEntity` to use `@ManyToOne` relationships for `user_reporter_id`, `user_reported_id`, and `auction_id`, so that the relationships are managed through JPA.
23. As a developer, I want the `ReviewResultEntity` to use `@ManyToOne` for `admin_id` and `auction_id`, and `@Enumerated(EnumType.STRING)` for `result`, so that the entity matches the updated SQL schema.
24. As a developer, I want the `AdminEntity` and `UserEntity` to remain unchanged (they already correctly use shared primary key with `ProfileEntity`), so that no unnecessary changes are made.
25. As a developer, I want the `RoleEntity`, `CategoryEntity`, and `ItemImageEntity` to remain unchanged, so that no unnecessary changes are made.
26. As a developer, I want the `AuthMapper` updated to handle nullable email and the new `@Enumerated(EnumType.STRING)` status, so that profile conversions work correctly.
27. As a developer, I want the `AuctionMapper` rewritten to flatten `UserEntity`→`ownerId`, `CategoryEntity`→`categoryId`, `ItemEntity`→`itemId` in `toDomain()`, and use `EntityManager` in `toEntity()`.
28. As a developer, I want the `ItemMapper` updated to flatten `AuctionEntity`→`auctionId` in `toDomain()` and use `EntityManager` in `toEntity()`.
29. As a developer, I want the `ReportMapper` updated to flatten `UserEntity`→`reporterId`/`reportedId`, `AuctionEntity`→`auctionId` in `toDomain()`, and use `EntityManager` in `toEntity()`.
30. As a developer, I want to run `mvn compile` after all changes to verify that everything compiles without errors.
31. As a developer, I want the SQL schema changes to be backward-compatible with the existing docker-compose setup, so that local development continues to work.
32. As a developer, I want the `AuctionEntity` to have `@OneToOne(mappedBy = "auction")` back-references to `ItemEntity` and `WinnerEntity`, so that the bidirectional relationships are properly mapped.
33. As a developer, I want the `AuctionEntity` to have `@OneToMany(mappedBy = "auction")` for `BidEntity` list, so that bids can be fetched via fetch joins.
34. As a developer, I want the `NotificationEntity` to use `@Enumerated` removed and `notificationType`/`entityType` stored as plain `String`, so that the entity matches the SQL `varchar` columns exactly.
35. As a developer, I want the domain `Notification` model to be flat with `List<Integer>` for recipient IDs, auction IDs, and report IDs, so that it serves as a lightweight DTO.
36. As a developer, I want the domain `ReviewResult` model to follow the same flat pattern as `ReportResult` (with `adminId` and `auctionId` as integers), so that consistency is maintained.
37. As a developer, I want the domain `Winner` model to keep `bidId` and `auctionId` as integers (no rich objects), so that it serves as a lightweight DTO.
38. As a developer, I want the `BiddingRulesService` and other domain services to remain unchanged, so that business logic is not affected by persistence refactoring.
39. As a developer, I want the `ImageStoragePort` and other port interfaces to remain unchanged, so that adapter boundaries are not affected.
40. As a developer, I want the `AuctionDraft`, `AuctionQuery`, `UserQuery`, and `ReportQuery` DTOs to remain unchanged, so that API contracts are not affected.

## Implementation Decisions

### Modules to Build/Modify

1. **SQL Schema Module** (`mysql/onlineBiddingProject.sql`)
   - Change `auction.status`, `profile.status`, `report_result.result`, `review_result.result` from `INT` to `VARCHAR(50)`
   - Update seed data to use enum string values

2. **Persistence Entity Module** (15 modified + 1 new entity)
   - Add `@ManyToOne`/`@OneToMany`/`@OneToOne` relationships to all entities with foreign keys
   - Remove bare FK integer fields where relationships are added
   - Complete rewrite of `NotificationEntity`
   - New standalone `AuctionUserEntity`
   - Add missing timestamps (`created_at`/`updated_at`) to `ItemEntity`, `NotificationEntity`
   - Fix `@OneToOne` join direction in `ReportEntity`→`ReportResultEntity`
   - Change enum mapping from `ORDINAL` to `STRING`
   - Fix nullable constraints (e.g., `ProfileEntity.email`)

3. **Domain Model Module** (3 new files)
   - New `ReviewResult` domain model
   - New `ReviewResultStatus` enum
   - New `ReviewResultRepository` interface

4. **Mapper Module** (5 modified + 1 new mapper)
   - Update all mappers to flatten JPA relationships back to IDs in `toDomain()`
   - Use `EntityManager.getReference()` in all `toEntity()` methods
   - Complete rewrite of `NotificationMapper`
   - New `ReviewResultMapper`

### Interfaces Modified

- `ReviewResultRepository` — new interface (mirrors `ReportResultRepository` pattern)
- `ItemRepository` — no interface change, but implementation may need fetch join queries
- `ReportRepository` — no interface change, but implementation needs fetch join for `ReportResult`
- `NotificationRepository` — no interface change, but implementation needs to handle join table queries

### Schema Changes

- `auction.status`: `INT NOT NULL` → `VARCHAR(50) NOT NULL`
- `profile.status`: `INT NOT NULL` → `VARCHAR(50) NOT NULL`
- `report_result.result`: `INT NOT NULL` → `VARCHAR(50) NOT NULL`
- `review_result.result`: `INT NOT NULL` → `VARCHAR(50) NOT NULL`

### Architectural Decisions

- **Flat domain models** — All domain models use IDs only, no nested rich objects. This keeps them lightweight as API DTOs.
- **JPA relationships** — Entities use `@ManyToOne`/`@OneToMany`/`@OneToOne` for all foreign keys. This enables fetch joins and lazy loading.
- **EntityManager in mappers** — `toEntity()` methods accept `EntityManager` parameter to create lazy proxies via `getReference()`.
- **No @ManyToMany** — Join tables (`auction_user`, `notification_auction`, `notification_report`) are standalone entities, not `@ManyToMany` collections.
- **One-directional domain references** — Notification domain model does not reference Auction/Report domain models; it uses `List<Integer>` IDs.

## Testing Decisions

### What Makes a Good Test

- Test external behavior (correct mapping between entity and domain), not implementation details (specific JPA annotations)
- Test that `toDomain()` correctly flattens relationships to IDs
- Test that `toEntity()` correctly creates entity with relationships from flat IDs
- Test edge cases: null optional relationships (e.g., `category_id` nullable on auction), nullable email on profile

### Modules to Test

1. **Mapper unit tests** — Each mapper's `toDomain()` and `toEntity()` methods can be tested in isolation with mock `EntityManager`
2. **Domain model tests** — `ReviewResult.judge()` factory method, `ReviewResultStatus` enum methods
3. **SQL schema validation** — Verify the SQL script runs without errors on a fresh MySQL instance (via docker-compose)

### Prior Art

- Existing tests in the codebase use in-memory implementations for repository testing (per ADR-0011)
- Domain models are tested as pure Java objects without Spring context
- Mapper tests can follow the same pattern: construct entities manually, call mapper methods, assert domain model fields

## Out of Scope

- **API controller changes** — Controllers and their DTOs are not affected by this refactoring
- **Service layer changes** — Application services and domain services are not affected
- **Repository interface changes** — Existing repository interfaces remain the same (implementation internals may change for fetch joins)
- **Security configuration** — No changes to authentication or authorization
- **WebSocket configuration** — No changes to real-time notification delivery
- **Quartz job configuration** — No changes to auction automation jobs
- **Cloudinary integration** — No changes to image storage
- **Frontend changes** — No changes to client-side code
- **Database migrations tool** — Changes are applied via raw SQL, not Flyway/Liquibase

## Further Notes

- This refactoring is a **prerequisite** for reliable persistence operations. Without entity-schema alignment, any JPA query or persist operation may fail at runtime.
- The SQL schema changes (INT→VARCHAR) are **breaking** for existing data. The seed data update script must be run after the ALTER TABLE statements.
- The `NotificationEntity` rewrite is the largest single change and should be verified carefully against the `notification`, `notification_auction`, `notification_report`, and `notification_notified` table structures.
- Fetch join queries should be added to repository implementations as needed — not all queries need all relationships loaded.
