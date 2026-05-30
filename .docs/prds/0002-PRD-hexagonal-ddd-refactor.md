# PRD: Hexagonal DDD Refactoring with Comprehensive Test Suite

## Problem Statement

The online bidding project currently uses a traditional layered architecture (controllers → services → repositories) with an anemic domain model. All business logic — auction lifecycle management, bidding rule enforcement, status transitions, score deduction — lives in service implementations. Domain entities are pure data holders with JPA annotations and getters/setters, containing zero behavior.

This creates several problems:

1. **Anemic domain model** — Business rules are scattered across service classes instead of being encapsulated in the entities that own the data. Changing a bidding rule requires finding and modifying `BidServiceImpl` rather than the `Bid` entity itself.

2. **Infrastructure leakage** — Application services directly access `SecurityContext` via static `CurrentUserUtils` calls (14 occurrences across 5 services), coupling business logic to Spring Security infrastructure.

3. **Persistence-coupled domain** — Domain entities are JPA `@Entity` classes with Hibernate annotations, making it impossible to test business logic without a database or Spring context.

4. **No test coverage** — The project has zero tests. The `src/test/` directory is empty. There is no safety net for refactoring or feature development.

5. **Flat package structure** — All 6 bounded contexts (Auth, Auction, Item, Report, Notification, Admin) share a single `models/` and `services/` package with no enforced boundaries.

6. **Fragile DTO mapping** — ModelMapper with magic string property names and a generic `copyNonNullProperties` utility make mapping behavior implicit and hard to debug.

7. **Coupled query building** — The `specification-arg-resolver` library generates JPA `Specification<T>` objects directly in controllers, leaking persistence concerns into the presentation layer.

## Solution

Restructure the entire codebase to follow hexagonal architecture (Ports & Adapters) with Domain-Driven Design principles, organized by bounded context. Simultaneously enrich the domain model with behavior, introduce value objects, establish clear adapter boundaries, and build a comprehensive test suite using Testcontainers for integration tests and in-memory port implementations for unit tests. No mocking frameworks are used — all test doubles are real, behaviorally-correct implementations.

The refactoring follows a strict migration order: Infrastructure foundation → Auth context → Item context → Auction context → Report context → Notification context → Admin context. Each context is a complete vertical slice: domain model → application service → adapters → tests.

## User Stories

### Phase 1: Infrastructure & Shared Foundation

1. As a developer, I want the package structure reorganized into `adapter/`, `application/`, `domain/`, and `infrastructure/` packages, so that the hexagonal architecture boundaries are enforced by package layout
2. As a developer, I want the `infrastructure/config/` package to hold all Spring configuration classes (WebSecurityConfiguration, WebSocketConfiguration, SwaggerConfig, MailConfiguration, ThymeleafTemplateConfiguration), so that configuration is isolated from business logic
3. As a developer, I want the `infrastructure/security/` package to hold JWT utilities, authentication filters, and UserDetails implementations, so that security infrastructure is contained
4. As a developer, I want the `infrastructure/quartz/` package to hold Quartz job classes (StartAuctionJob, EndAuctionJob, CancelAuctionJob), so that scheduling infrastructure is isolated
5. As a developer, I want the `infrastructure/websocket/` package to hold WebSocket channel interceptors and push services, so that real-time infrastructure is contained
6. As a developer, I want the `infrastructure/util/` package to hold utility classes (HttpHeadersUtils, PaginationUtils, JobDetailBuilderUtils), so that cross-cutting utilities are organized
7. As a developer, I want the `domain/event/EventPublisher.java` port interface defined, so that the domain can publish events without depending on Spring's `ApplicationEventPublisher`
8. As a developer, I want the `domain/event/DomainEvent.java` base interface defined, so that all domain events have a common type
9. As a developer, I want all existing domain events (SignupCompleteEvent) migrated to implement `DomainEvent`, so that events follow the domain event pattern
10. As a developer, I want a Spring adapter implementing `EventPublisher` in `infrastructure/events/`, so that domain events are published via Spring's event system
11. As a developer, I want a test helper `CurrentUserTestHelper` that sets up a mock SecurityContext for integration tests, so that authenticated endpoints can be tested without real JWT tokens

### Phase 2: Auth Context

12. As a developer, I want `domain/model/auth/Profile.java` as a rich entity with behavior (not a JPA entity), so that auth business rules are encapsulated in the domain model
13. As a developer, I want `domain/model/auth/User.java` extending Profile with meaningful behavior (`canBidOn()`, `canCreateAuction()`, `canReport()`), so that user-specific authorization rules are domain-driven
14. As a developer, I want `domain/model/auth/Admin.java` extending Profile with meaningful behavior (`canApproveAuction()`, `canBanUser()`, `canJudgeReport()`), so that admin-specific authorization rules are domain-driven
15. As a developer, I want `domain/model/auth/Role.java` as a value object replacing the current JPA entity, so that role representation is persistence-agnostic
16. As a developer, I want `domain/model/auth/UserStatus.java` as a value object (BANNED, SUSPENDED, ACTIVE, INACTIVE) with behavior (`isActive()`, `isSuspended()`, `canAuthenticate()`), so that status logic is in the domain
17. As a developer, I want `domain/model/auth/VerificationToken.java` as a domain entity with behavior (`isExpired()`, `extend()`), so that token lifecycle rules are encapsulated
18. As a developer, I want `domain/model/auth/UserRepository.java` as a port interface with lean methods (findById, save, findByUsername, existsByUsername, existsByEmail), so that the domain defines its persistence contract
19. As a developer, I want `domain/model/auth/UserQuery.java` as a query object for admin user searches (status filter, name filter, pagination), so that query intent is a domain concept
20. As a developer, I want `application/AuthService.java` port interface with methods: login, signUp, changePassword, validateToken, resendVerification, so that auth use cases are explicitly defined
21. As a developer, I want `application/impl/AuthServiceImpl.java` receiving `userId` as a parameter instead of accessing `CurrentUserUtils`, so that the application layer has no infrastructure dependency
22. As a developer, I want `adapter/in/auth/AuthController.java` handling HTTP requests and building request objects, so that the presentation layer is isolated
23. As a developer, I want `adapter/in/auth/dto/` containing UserLogin, UserSignup, UserChangePassword, UserAuthResponse DTOs, so that API contracts are adapter-level concerns
24. As a developer, I want `adapter/out/repository/database/persistence/ProfileEntity.java` as the JPA entity with all Hibernate annotations, so that persistence concerns are isolated from the domain
25. As a developer, I want `adapter/out/repository/database/persistence/UserEntity.java` and `AdminEntity.java` as JPA entities with shared primary key inheritance, so that the database mapping is explicit
26. As a developer, I want `adapter/out/repository/database/mapper/AuthMapper.java` as a static utility class with `toDomain()` and `toEntity()` methods, so that mapping is explicit and testable
27. As a developer, I want `adapter/out/repository/database/UserRepositoryImpl.java` implementing the `UserRepository` port, so that the outbound adapter bridges domain and persistence
28. As a developer, I want `adapter/out/repository/database/springdata/ProfileJpaRepository.java` as a Spring Data JPA interface, so that the JPA implementation detail is contained
29. As a developer, I want `adapter/out/email/EmailServiceImpl.java` implementing a mail port, so that email infrastructure is an outbound adapter
30. As a developer, I want `domain/model/auth/ProfileTest.java` unit tests covering all Profile, User, and Admin behavior methods, so that auth domain logic is verified
31. As a developer, I want `application/impl/AuthServiceImplTest.java` unit tests using in-memory repository implementations, so that auth use cases are tested without Spring
32. As a developer, I want `adapter/in/auth/AuthControllerIT.java` integration tests using Testcontainers MySQL, so that the full auth flow works end-to-end

### Phase 3: Item Context

33. As a developer, I want `domain/model/item/Item.java` as a rich entity with behavior (`addImage()`, `removeImage()`, `getImageCount()`), so that item business rules are in the domain
34. As a developer, I want `domain/model/item/ItemImage.java` as a value object or entity with behavior (`hasCloudinaryId()`), so that image management rules are encapsulated
35. As a developer, I want `domain/model/item/ItemRepository.java` port interface, so that item persistence is a domain contract
36. As a developer, I want `application/ItemService.java` port and `application/impl/ItemServiceImpl.java` implementation, so that item use cases are defined
37. As a developer, I want `adapter/in/item/ItemController.java` and DTOs, so that item API endpoints are adapter concerns
38. As a developer, I want `adapter/out/repository/database/persistence/ItemEntity.java` and `ItemImageEntity.java`, so that item persistence mapping is isolated
39. As a developer, I want `adapter/out/repository/database/mapper/ItemMapper.java` static utility, so that item mapping is explicit
40. As a developer, I want `adapter/out/cloudinary/CloudinaryAdapter.java` as an outbound adapter implementing an image storage port, so that Cloudinary is an infrastructure detail
41. As a developer, I want `domain/model/item/ItemTest.java` unit tests, so that item domain logic is verified
42. As a developer, I want `application/impl/ItemServiceImplTest.java` unit tests with in-memory ports, so that item use cases are tested without Spring

### Phase 4: Auction Context

43. As a developer, I want `domain/model/auction/Auction.java` as a rich entity with behavior: `create()`, `submitForReview()`, `approve()`, `reject()`, `start()`, `end()`, `cancel()`, `transitionTo()`, so that auction lifecycle rules are encapsulated
44. As a developer, I want `domain/model/auction/AuctionStatus.java` as a value object with behavior (`canTransitionTo()`, `isTerminal()`, `canBeBiddedOn()`), replacing the current enum, so that status state machine logic is in the domain
45. As a developer, I want `domain/model/auction/Money.java` as a value object with behavior (`isGreaterThan()`, `isGreaterThanOrEqualTo()`, `add()`, `multiply()`, `zero()`), replacing primitive `Double` fields, so that monetary calculations are type-safe and encapsulated
46. As a developer, I want `domain/model/auction/Bid.java` as a rich entity with behavior (`isValidFor()`, `isHigherThan()`), so that bid validation rules are in the domain
47. As a developer, I want `domain/model/auction/Winner.java` as a domain entity, so that winner determination is a domain concept
48. As a developer, I want `domain/model/auction/Category.java` as a value object, so that category is persistence-agnostic
49. As a developer, I want `domain/model/auction/AuctionRepository.java` port interface with lean methods (findById, save, deleteById, find, findPaged), so that auction persistence is a domain contract
50. As a developer, I want `domain/model/auction/AuctionQuery.java` query object with fields (ownerId, status, description, categoryId, sortBy, direction), so that search intent is a domain concept
51. As a developer, I want `domain/service/BiddingRulesService.java` containing cross-aggregate bidding rules (validate bid amount, validate bidder eligibility, enforce 3x cap), so that complex bidding logic that touches multiple aggregates is a domain service
52. As a developer, I want `domain/service/AuctionLifecycleService.java` containing cross-aggregate lifecycle orchestration (schedule Quartz jobs on approval, determine winner on end), so that lifecycle coordination is a domain service
53. As a developer, I want `domain/event/AuctionCreatedEvent.java`, `AuctionApprovedEvent.java`, `AuctionEndedEvent.java`, `BidPlacedEvent.java` implementing `DomainEvent`, so that auction events follow the domain event pattern
54. As a developer, I want `application/AuctionService.java` port and `application/impl/AuctionServiceImpl.java` receiving `userId` as parameter, so that auction use cases have no infrastructure dependency
55. As a developer, I want `application/BidService.java` port and `application/impl/BidServiceImpl.java` receiving `userId` as parameter, so that bid use cases have no infrastructure dependency
56. As a developer, I want `adapter/in/auction/AuctionController.java` building AuctionQuery from @RequestParam instead of using @Spec annotations, so that query building is explicit and persistence-agnostic
57. As a developer, I want `adapter/in/auction/dto/` containing AuctionRequestDto, BidRequestDto, AuctionResponse, BidResponse, so that API contracts are adapter concerns
58. As a developer, I want `adapter/out/repository/database/persistence/AuctionEntity.java`, `BidEntity.java`, `WinnerEntity.java`, `CategoryEntity.java`, so that auction persistence mapping is isolated
59. As a developer, I want `adapter/out/repository/database/mapper/AuctionMapper.java`, `BidMapper.java` static utilities, so that auction mapping is explicit
60. As a developer, I want `adapter/out/repository/database/AuctionRepositoryImpl.java` translating AuctionQuery to JPA Specification internally, so that the Specification concern is contained in the adapter
61. As a developer, I want `adapter/out/repository/database/springdata/AuctionJpaRepository.java` as Spring Data JPA interface with native SQL queries, so that JPA-specific queries are isolated
62. As a developer, I want `domain/model/auction/AuctionTest.java` unit tests covering all status transitions, creation rules, and invariant enforcement, so that auction domain logic is verified
63. As a developer, I want `domain/model/auction/BidTest.java` unit tests covering all bidding rules (minimum price, step enforcement, 3x cap, self-bidding prevention), so that bid domain logic is verified
64. As a developer, I want `domain/model/auction/MoneyTest.java` unit tests covering all monetary operations, so that value object arithmetic is verified
65. As a developer, I want `domain/service/BiddingRulesServiceTest.java` unit tests, so that cross-aggregate bidding rules are verified
66. As a developer, I want `application/impl/AuctionServiceImplTest.java` and `BidServiceImplTest.java` unit tests with in-memory ports, so that auction and bid use cases are tested without Spring
67. As a developer, I want `adapter/in/auction/AuctionControllerIT.java` integration tests covering full auction lifecycle (create → submit → approve → bid → end), so that the complete auction flow works end-to-end with real MySQL

### Phase 5: Report Context

68. As a developer, I want `domain/model/report/Report.java` as a rich entity with behavior (`submit()`, `canBeJudgedBy()`, `hasResult()`), so that report business rules are in the domain
69. As a developer, I want `domain/model/report/ReportResult.java` as a domain entity with behavior (`isAccepted()`, `isRejected()`), so that report judgment logic is encapsulated
70. As a developer, I want `domain/model/report/ReportImage.java` as a domain entity, so that report images are a domain concept
71. As a developer, I want `domain/model/report/ReportRepository.java` port interface, so that report persistence is a domain contract
72. As a developer, I want `domain/model/report/ReportQuery.java` query object for admin report searches, so that report query intent is a domain concept
73. As a developer, I want `domain/service/LegitimateScoreService.java` containing the cross-aggregate rule: when a report is accepted, deduct 5 points from the reported user's legitimate score, so that score deduction logic is a domain service
74. As a developer, I want `domain/event/ReportSubmittedEvent.java` and `ReportJudgedEvent.java` implementing `DomainEvent`, so that report events follow the domain event pattern
75. As a developer, I want `application/ReportService.java` port and `application/impl/ReportServiceImpl.java` receiving `userId` as parameter, so that report use cases have no infrastructure dependency
76. As a developer, I want `adapter/in/report/ReportController.java` and DTOs, so that report API endpoints are adapter concerns
77. As a developer, I want `adapter/out/repository/database/persistence/ReportEntity.java`, `ReportResultEntity.java`, `ReportImageEntity.java`, so that report persistence mapping is isolated
78. As a developer, I want `adapter/out/repository/database/mapper/ReportMapper.java` static utility, so that report mapping is explicit
79. As a developer, I want `domain/model/report/ReportTest.java` unit tests covering submission rules, judgment rules, and invariant enforcement, so that report domain logic is verified
80. As a developer, I want `domain/service/LegitimateScoreServiceTest.java` unit tests, so that score deduction logic is verified
81. As a developer, I want `application/impl/ReportServiceImplTest.java` unit tests with in-memory ports, so that report use cases are tested without Spring
82. As a developer, I want `adapter/in/report/ReportControllerIT.java` integration tests covering report submission and admin judgment, so that report flows work end-to-end

### Phase 6: Notification Context

83. As a developer, I want `domain/model/notification/Notification.java` as a rich entity with behavior (`linkToAuction()`, `linkToReport()`, `addRecipient()`), so that notification construction rules are in the domain
84. As a developer, I want `domain/model/notification/NotificationType.java` as a value object (SUBMIT_AUCTION, REVIEW_AUCTION, START_AUCTION, END_AUCTION, NEW_BID_AUCTION, CREATE_REPORT, JUDGE_REPORT), so that notification types are a domain concept
85. As a developer, I want `domain/model/notification/NotificationRepository.java` port interface, so that notification persistence is a domain contract
86. As a developer, I want a `domain/port/WebSocketPort.java` interface for pushing real-time notifications, so that WebSocket delivery is an outbound port
87. As a developer, I want `application/NotificationService.java` port and `application/impl/NotificationServiceImpl.java`, so that notification use cases are defined
88. As a developer, I want `adapter/in/websocket/WebSocketPublisherAdapter.java` implementing `WebSocketPort`, so that WebSocket is an outbound adapter
89. As a developer, I want `adapter/out/repository/database/persistence/NotificationEntity.java` and related entities, so that notification persistence mapping is isolated
90. As a developer, I want `adapter/out/repository/database/mapper/NotificationMapper.java` static utility, so that notification mapping is explicit
91. As a developer, I want `domain/model/notification/NotificationTest.java` unit tests, so that notification domain logic is verified
92. As a developer, I want `application/impl/NotificationServiceImplTest.java` unit tests with in-memory ports, so that notification use cases are tested without Spring

### Phase 7: Admin Context

93. As a developer, I want admin use cases refactored to use the same domain models (Auction, Report, Profile) with admin-specific behavior on User/Admin entities, so that admin operations reuse domain logic
94. As a developer, I want `adapter/in/admin/AdminAuctionController.java`, `AdminProfileController.java`, `AdminReportController.java` with DTOs, so that admin API endpoints are adapter concerns
95. As a developer, I want admin controllers building query objects from @RequestParam, so that admin filtering is explicit
96. As a developer, I want `adapter/in/admin/dto/` containing admin-specific request/response DTOs, so that admin API contracts are adapter concerns
97. As a developer, I want integration tests for admin flows (approve auction, ban user, judge report), so that admin operations work end-to-end

### Phase 8: Cleanup & Verification

98. As a developer, I want the `models/` package (entities, dtos, requests, responses) completely removed, so that the old structure is fully replaced
99. As a developer, I want the `services/` and `services/impl/` packages completely removed, so that the old service layer is fully replaced
100. As a developer, I want the `repositories/` package completely removed, so that the old repository layer is fully replaced
101. As a developer, I want the `controllers/` package completely removed, so that the old controller layer is fully replaced
102. As a developer, I want the `constants/` package eliminated — value objects and enums in the domain replace primitive obsession, so that magic constants are gone
103. As a developer, I want the `daos/` package removed and native SQL queries moved to Spring Data JPA interfaces in the adapter layer, so that data access is properly layered
104. As a developer, I want the `events/` and `events/listeners/` packages removed and replaced by domain events in `domain/event/` with infrastructure listeners in `infrastructure/events/`, so that event handling follows hexagonal boundaries
105. As a developer, I want `mvn clean compile` to succeed with zero warnings from the new structure, so that the build is clean
106. As a developer, I want all unit tests passing without any Spring context, so that domain and application logic is verified in isolation
107. As a developer, I want all integration tests passing with Testcontainers MySQL, so that the full stack works end-to-end
108. As a developer, I want `docker-compose up` to start the application successfully with the new structure, so that the deployment is verified
109. As a developer, I want the README updated to reflect the new hexagonal architecture with package structure diagram, so that documentation matches the code
110. As a developer, I want a new ADR documenting the hexagonal DDD refactoring decision and the trade-offs made, so that the architectural rationale is preserved

## Implementation Decisions

### Modules to Build/Modify

**Domain Layer (new)** — Pure Java, zero framework dependencies. Contains:
- Domain models (entities with behavior) per bounded context
- Value objects (Money, AuctionStatus, UserStatus, Role, Email)
- Repository port interfaces
- Query objects for search intent
- Domain services for cross-aggregate logic
- Domain events and EventPublisher port

**Application Layer (new)** — Use case orchestration. Contains:
- Application service interfaces (ports)
- Application service implementations
- Receives userId as parameter, never accesses SecurityContext

**Adapter Layer (new)** — All I/O concerns. Contains:
- Inbound: REST controllers, request/response DTOs, query object builders
- Outbound: JPA repository implementations, persistence entities, static mappers, Spring Data interfaces, Cloudinary adapter, WebSocket adapter, email adapter

**Infrastructure Layer (new)** — Cross-cutting concerns. Contains:
- Spring configuration (security, WebSocket, Swagger, mail, Thymeleaf)
- Security (JWT, filters, UserDetails)
- Quartz jobs
- Utilities (CurrentUserUtils, HttpHeadersUtils, PaginationUtils)

### Interfaces Modified

- `UserRepository` port — replaces Spring Data `ProfileRepository` and `UserRepository`
- `AuctionRepository` port — replaces Spring Data `AuctionRepository` and `AuctionDao`
- `BidRepository` port — replaces Spring Data `BidRepository`
- `ItemRepository` port — replaces Spring Data `ItemRepository` and `ItemImageRepository`
- `ReportRepository` port — replaces Spring Data `ReportRepository`, `ReportResultRepository`, `ReportImageRepository`
- `NotificationRepository` port — replaces Spring Data `NotificationRepository` and related repositories
- `EventPublisher` port — replaces direct `ApplicationEventPublisher` usage
- `WebSocketPort` port — replaces direct `SimpMessagingTemplate` usage
- `ImageStoragePort` port — replaces direct Cloudinary API calls

### Architectural Decisions

1. **Single module, package-per-context** — Multi-module Maven adds build complexity not justified at 120 files. Package-level boundaries with clear naming conventions enforce separation.

2. **Separate domain and persistence models** — Domain model is persistence-agnostic (no JPA annotations). Persistence entities live in adapter layer. Static mappers bridge the two. This enables swapping JPA for any persistence technology.

3. **Rich domain model** — Entities enforce their own invariants. Business rules like auction status transitions, bidding validation, and score deduction live on the entities. Domain services only handle cross-aggregate coordination.

4. **User and Admin inheritance with behavior** — Profile is the base entity. User adds `canBidOn()`, `canCreateAuction()`, `canReport()`. Admin adds `canApproveAuction()`, `canBanUser()`, `canJudgeReport()`. Inheritance earns its place through meaningful polymorphic behavior.

5. **Pass userId as parameter** — Application services receive the caller's identity as an Integer parameter extracted by the controller from SecurityContext. No static utility calls, no infrastructure leakage into domain/application.

6. **Lean repository ports with query objects** — Repository interfaces have minimal methods (findById, save, deleteById, find, findPaged). Complex queries use domain query objects (AuctionQuery, ReportQuery). JPA Specification generation is an adapter-internal concern.

7. **Static utility mappers** — Mappers are pure functions (input → output). No Spring beans, no injection. Easy to test, stateless, composable.

8. **Domain events with port** — Domain defines DomainEvent interface and EventPublisher port. Infrastructure implements the port using Spring's ApplicationEventPublisher. Domain has zero Spring dependency.

9. **Remove specification-arg-resolver** — Controllers build query objects from @RequestParam directly. No JPA Specification in controllers. The dependency is removed from pom.xml.

10. **Remove ModelMapper** — All mapping is manual via static utility classes. The dependency is removed from pom.xml.

11. **No Mockito** — Unit tests use in-memory port implementations. Integration tests use Testcontainers with real MySQL. No mocking frameworks in the test dependency tree.

12. **Migration order: Infrastructure → Auth → Item → Auction → Report → Notification → Admin** — Each context is a complete vertical slice. Auth is the foundation (everything depends on Profile/User/Admin). Admin is last (it orchestrates all other contexts).

### Schema Changes

None. The database schema remains identical. Persistence entities in the adapter layer map to the same tables. The refactoring is purely structural.

### API Contracts

None. REST API paths, request/response formats, and behavior remain identical. The controller layer moves from `controllers/` to `adapter/in/` but the HTTP interface is unchanged.

## Testing Decisions

### What Makes a Good Test

Tests verify **external behavior**, not implementation details. A good test:
- Calls a public method or HTTP endpoint with known input
- Asserts on the output or observable side effects (database state, events published)
- Does not assert on internal method calls, private state, or mock invocation counts
- Uses real objects whenever possible (in-memory implementations, real databases)

### Test Categories

**Unit Tests (domain model)** — Pure Java, no Spring context, no database. Test entities, value objects, and domain services in isolation. Use in-memory port implementations for any repository dependencies. Run in milliseconds.

Example: `AuctionTest.java` tests that `auction.submitForReview()` transitions status from DRAFT to PENDING, throws if status is not DRAFT, throws if item is null.

**Unit Tests (application service)** — Pure Java, no Spring context. Test use case orchestration with in-memory port implementations. Verify that the application service correctly delegates to domain model and ports.

Example: `AuctionServiceImplTest.java` tests that `createAuction(dto, userId)` creates an Auction domain object, saves it via the repository port, and returns the result.

**Integration Tests (adapter)** — Full Spring context with Testcontainers MySQL. Test that adapters correctly bridge domain and infrastructure. Verify JPA queries, REST endpoints, and end-to-end flows.

Example: `AuctionControllerIT.java` tests the full lifecycle: POST /api/v1/auctions (create) → PUT /api/v1/auctions/{id}/submit (submit) → PUT /api/v1/admin/auctions/{id}/approve (approve) → POST /api/v1/auctions/{id}/bids (bid) → wait for Quartz job (end).

### Test Framework Stack

| Tool | Purpose |
|---|---|
| JUnit 5 | Test runner (already in spring-boot-starter-test) |
| AssertJ | Fluent assertions |
| Testcontainers | Real MySQL 8 in Docker for integration tests |
| MockMvc | HTTP layer testing for controllers |
| Spring Boot Test | @SpringBootTest, @AutoConfigureMockMvc for integration |

**No Mockito.** In-memory implementations replace all mocks.

### Modules to Test

| Module | Test Type | Scope |
|---|---|---|
| `domain/model/auction/Auction` | Unit | All status transitions, invariants, creation rules |
| `domain/model/auction/Bid` | Unit | All bidding rules, validation |
| `domain/model/auction/Money` | Unit | All arithmetic operations, comparisons |
| `domain/model/auction/AuctionStatus` | Unit | State machine transitions |
| `domain/model/auth/Profile` | Unit | Status behavior, legitimate score |
| `domain/model/auth/User` | Unit | canBidOn, canCreateAuction, canReport |
| `domain/model/auth/Admin` | Unit | canApproveAuction, canBanUser, canJudgeReport |
| `domain/model/auth/VerificationToken` | Unit | Expiry, extension |
| `domain/model/item/Item` | Unit | Image management |
| `domain/model/report/Report` | Unit | Submission, judgment rules |
| `domain/model/notification/Notification` | Unit | Linking to entities |
| `domain/service/BiddingRulesService` | Unit | Cross-aggregate bid validation |
| `domain/service/AuctionLifecycleService` | Unit | Cross-aggregate lifecycle |
| `domain/service/LegitimateScoreService` | Unit | Cross-aggregate score deduction |
| `application/impl/AuthServiceImpl` | Unit | Auth use cases with in-memory ports |
| `application/impl/AuctionServiceImpl` | Unit | Auction use cases with in-memory ports |
| `application/impl/BidServiceImpl` | Unit | Bid use cases with in-memory ports |
| `application/impl/ItemServiceImpl` | Unit | Item use cases with in-memory ports |
| `application/impl/ReportServiceImpl` | Unit | Report use cases with in-memory ports |
| `application/impl/NotificationServiceImpl` | Unit | Notification use cases with in-memory ports |
| `adapter/in/auth/AuthControllerIT` | Integration | Login, signup, verify, change password |
| `adapter/in/auction/AuctionControllerIT` | Integration | Full auction lifecycle |
| `adapter/in/auction/BidControllerIT` | Integration | Place bid, validate rules |
| `adapter/in/admin/AdminAuctionControllerIT` | Integration | Approve, reject, delete |
| `adapter/in/admin/AdminProfileControllerIT` | Integration | Ban, suspend, activate |
| `adapter/in/admin/AdminReportControllerIT` | Integration | Judge report |
| `adapter/in/report/ReportControllerIT` | Integration | Submit report, add images |
| `adapter/out/repository/database/AuctionRepositoryImplIT` | Integration | JPA queries, save, find |
| `adapter/out/repository/database/ProfileRepositoryImplIT` | Integration | JPA queries, save, find |

### Prior Art

No existing tests in the codebase. This is a greenfield test suite. Test patterns follow standard Spring Boot testing practices with Testcontainers.

## Out of Scope

- **Database schema changes** — The schema is untouched. Only the code structure changes.
- **API contract changes** — No new endpoints, no changed response formats, no versioned API.
- **Frontend changes** — Backend only. WebSocket clients, email templates, and API consumers are not modified.
- **Performance optimization** — No query optimization, caching, or connection pooling changes.
- **Microservice decomposition** — The monolithic architecture is preserved with package-level boundaries.
- **Flyway/Liquibase** — Database migration tooling is not added.
- **CI/CD pipeline changes** — No new stages or deployment changes beyond what was done in the Java 17 migration.
- **OAuth2 integration** — The `spring-boot-starter-oauth2-client` dependency is present but not actively used; it remains untouched.
- **New business features** — This is a refactoring only. No new domain capabilities are added.
- **Logging improvements** — Log4j2 configuration and structured logging are separate concerns.

## Further Notes

### Migration Risk Assessment

The highest-risk changes:
1. **Auction lifecycle state machine** — The most complex domain logic. Status transitions must be identical after refactoring. Comprehensive unit tests on `Auction.transitionTo()` are critical.
2. **Bidding rules** — The 3x cap, step enforcement, and self-bidding prevention must be preserved exactly. Integration tests with real MySQL ensure concurrent bid handling works.
3. **Security context decoupling** — Moving from `CurrentUserUtils.getCurrentUserDetails()` to `userId` parameter requires updating every controller and service method signature. Careful attention to not miss any call site.

The lowest-risk changes:
1. **Package restructuring** — Moving files between packages. No behavioral change.
2. **Value object introduction** — Money, AuctionStatus, UserStatus are type-safe wrappers around existing primitives.
3. **Mapper creation** — Explicit versions of what ModelMapper was doing implicitly.

### Execution Strategy

Each bounded context is committed separately after all its tests pass. The migration order ensures that dependencies are satisfied:
- Phase 1 (Infrastructure) is committed first — it sets up the package structure and shared types
- Phase 2 (Auth) depends on Phase 1 — Profile/User/Admin are the foundation
- Phase 3 (Item) depends on Phase 1 — independent, good warm-up
- Phase 4 (Auction) depends on Phase 2 + 3 — core aggregate
- Phase 5 (Report) depends on Phase 2 + 4 — uses Auth and Auction
- Phase 6 (Notification) depends on all previous — triggered by all contexts
- Phase 7 (Admin) depends on all previous — orchestrates all contexts
- Phase 8 (Cleanup) removes old packages after all contexts are migrated

### Post-Migration Verification

After each phase:
1. `mvn clean compile` succeeds
2. All unit tests pass (no Spring context)
3. All integration tests pass (Testcontainers MySQL)

After all phases:
4. `docker-compose up` starts the application
5. Swagger UI loads at `/swagger-ui/index.html`
6. All existing API endpoints work identically
7. WebSocket connection establishes for real-time bidding
8. Quartz jobs fire correctly for auction lifecycle
