# PRD: Service Test Refactoring for Hexagonal Architecture

## Problem Statement

The online bidding project has successfully migrated to a hexagonal architecture with clear separation between domain, application, adapter, and infrastructure layers. However, the test suite for application service implementations has several architectural issues that violate the dependency inversion principle and limit test coverage:

1. **Tests couple to implementations** — Test classes directly instantiate concrete `InMemory*` implementations (e.g., `InMemoryBidRepository`) rather than depending on repository interfaces (e.g., `BidRepository`), making tests aware of implementation details

2. **Missing database repository tests** — Current tests only exercise in-memory repository implementations, providing no verification that service logic works correctly with the actual PostgreSQL/MySQL database repositories

3. **No separation of concerns in testing** — The same test class attempts to cover both unit-level behavior (in-memory) and integration-level behavior (database), leading to confused test intent

4. **Manual dependency setup** — Tests manually construct service implementations with concrete types, rather than using a consistent dependency provision mechanism

5. **Incomplete test coverage** — Two services (CategoryService and JobSchedulerService) have no tests at all

## Solution

Refactor the application service test suite to follow hexagonal architecture testing principles:

1. **Depend on abstractions** — All tests reference repository and port **interfaces** only, never concrete implementations

2. **Separate test variants** — Create two distinct test classes per service:
   - `*InMemoryTest` — Pure unit tests using in-memory implementations (fast, isolated)
   - `*DatabaseTest` — Integration tests using real database repositories (accurate, comprehensive)

3. **Test factory pattern** — Introduce `TestRepositoryFactory` and `TestPortFactory` to provide in-memory implementations for unit tests, enabling consistent dependency provision

4. **Base test classes** — Create `BaseInMemoryTest` for common unit test setup and leverage existing `BaseIntegrationTest` for database tests

5. **Complete coverage** — Add missing tests for CategoryService and JobSchedulerService

## User Stories

### Test Infrastructure

1. As a developer, I want a `TestRepositoryFactory` that provides in-memory implementations for all repository interfaces, so that unit tests can obtain dependencies without coupling to implementations
2. As a developer, I want a `TestPortFactory` that provides in-memory implementations for all port interfaces plus EventPublisher, so that unit tests can obtain external dependencies without coupling to infrastructure
3. As a developer, I want a `BaseInMemoryTest` abstract base class that provides factory-accessed dependencies, so that in-memory test setup is consistent and DRY
4. As a developer, I want an `InMemoryScheduler` implementation for testing JobSchedulerService, so that Quartz job scheduling can be verified in isolation

### Auth Service Tests

5. As a developer, I want `AuthServiceImplInMemoryTest` to verify login, signup, password change, and token validation using in-memory repositories, so that auth use cases are unit-tested without Spring context
6. As a developer, I want `AuthServiceImplInMemoryTest` to test UserStatus-based authentication rules (ACTIVE, INACTIVE, SUSPENDED, BANNED), so that status-based auth behavior is verified
7. As a developer, I want `AuthServiceImplInMemoryTest` to test duplicate username/email rejection, so that uniqueness invariants are enforced
8. As a developer, I want `AuthServiceImplInMemoryTest` to test admin account creation prevention, so that business rules are validated
9. As a developer, I want `AuthServiceImplInMemoryTest` to test verification token expiration handling, so that token lifecycle is correct
10. As a developer, I want `AuthServiceImplInMemoryTest` to test password change validation (matching confirmation), so that password rules are enforced
11. As a developer, I want `AuthServiceImplDatabaseTest` to verify login, signup, and password change with real database repositories, so that auth use cases work with PostgreSQL/MySQL
12. As a developer, I want `AuthServiceImplDatabaseTest` to extend `BaseIntegrationTest` for automatic database cleanup, so that tests are isolated
13. As a developer, I want `AuthServiceImplDatabaseTest` to use `@Autowired` on repository interfaces, so that Spring injects the correct database implementations

### Auction Service Tests

14. As a developer, I want `AuctionServiceImplInMemoryTest` to verify auction creation with AuctionDraft, so that auction initialization is correct
15. As a developer, I want `AuctionServiceImplInMemoryTest` to test auction lifecycle transitions (DRAFT → PENDING → QUEUED → OPENING → ENDED), so that state machine behavior is verified
16. As a developer, I want `AuctionServiceImplInMemoryTest` to test auction submission rejection for non-owners, so that ownership rules are enforced
17. As a developer, I want `AuctionServiceImplInMemoryTest` to test admin review approve/reject, so that admin workflow is validated
18. As a developer, I want `AuctionServiceImplInMemoryTest` to test auction deletion by owner, so that delete authorization is verified
19. As a developer, I want `AuctionServiceImplInMemoryTest` to test my auctions query with status filter, so that listing functionality works
20. As a developer, I want `AuctionServiceImplDatabaseTest` to verify auction lifecycle with real database repositories, so that auction use cases work with PostgreSQL/MySQL
21. As a developer, I want `AuctionServiceImplDatabaseTest` to test auction query with AuctionQuery object, so that search and filtering work end-to-end

### Bid Service Tests

22. As a developer, I want `BidServiceImplInMemoryTest` to verify bid placement with valid amount, so that bidding works correctly
23. As a developer, I want `BidServiceImplInMemoryTest` to test bid rejection on non-OPENING auctions, so that status-based bidding rules are enforced
24. As a developer, I want `BidServiceImplInMemoryTest` to test bid rejection from auction owner, so that self-bidding prevention works
25. As a developer, I want `BidServiceImplInMemoryTest` to test bid rejection below start price, so that minimum bid rules are enforced
26. As a developer, I want `BidServiceImplInMemoryTest` to test bid price step enforcement, so that bidding increments are validated
27. As a developer, I want `BidServiceImplInMemoryTest` to test retrieving bids by auction ID, so that bid history is accessible
28. As a developer, I want `BidServiceImplInMemoryTest` to verify BidPlacedEvent publication, so that domain events are triggered
29. As a developer, I want `BidServiceImplDatabaseTest` to verify bid placement with real database repositories, so that bidding works with PostgreSQL/MySQL
30. As a developer, I want `BidServiceImplDatabaseTest` to test highest bid retrieval, so that bid comparison works end-to-end

### Item Service Tests

31. As a developer, I want `ItemServiceImplInMemoryTest` to verify item creation with name, description, and amount, so that item initialization is correct
32. As a developer, I want `ItemServiceImplInMemoryTest` to test item update by owner, so that update authorization is enforced
33. As a developer, I want `ItemServiceImplInMemoryTest` to test update rejection for non-existent items, so that error handling works
34. As a developer, I want `ItemServiceImplInMemoryTest` to test image addition with Cloudinary port, so that image upload integration works
35. As a developer, I want `ItemServiceImplInMemoryTest` to test image deletion, so that image removal works
36. As a developer, I want `ItemServiceImplInMemoryTest` to test item retrieval by ID, so that fetch functionality is verified
37. As a developer, I want `ItemServiceImplInMemoryTest` to test bulk image deletion, so that cleanup functionality works
38. As a developer, I want `ItemServiceImplDatabaseTest` to verify item CRUD with real database repositories, so that item use cases work with PostgreSQL/MySQL
39. As a developer, I want `ItemServiceImplDatabaseTest` to verify image metadata persistence, so that image associations work end-to-end

### Notification Service Tests

40. As a developer, I want `NotificationServiceImplInMemoryTest` to verify auction notification creation, so that auction events trigger notifications
41. As a developer, I want `NotificationServiceImplInMemoryTest` to test report notification creation, so that report events trigger notifications
42. As a developer, I want `NotificationServiceImplInMemoryTest` to test my notifications query with pagination, so that notification listing works
43. As a developer, I want `NotificationServiceImplInMemoryTest` to test auction notification deletion, so that cleanup works
44. As a developer, I want `NotificationServiceImplInMemoryTest` to test report notification deletion, so that cleanup works
45. As a developer, I want `NotificationServiceImplInMemoryTest` to verify WebSocketPort.sendToUser calls, so that real-time delivery is triggered
46. As a developer, I want `NotificationServiceImplDatabaseTest` to verify notification persistence with real database repositories, so that notification use cases work with PostgreSQL/MySQL

### Report Service Tests

47. As a developer, I want `ReportServiceImplInMemoryTest` to verify report submission, so that report creation works
48. As a developer, I want `ReportServiceImplInMemoryTest` to test report retrieval by ID, so that fetch functionality is verified
49. As a developer, I want `ReportServiceImplInMemoryTest` to test admin judgment with ACCEPTED status, so that score deduction is triggered
50. As a developer, I want `ReportServiceImplInMemoryTest` to test admin judgment with REJECTED status, so that rejection works
51. As a developer, I want `ReportServiceImplInMemoryTest` to test already-judged report rejection, so that double-judgment prevention works
52. As a developer, I want `ReportServiceImplInMemoryTest` to verify legitimate score deduction on accepted reports, so that business rules are enforced
53. As a developer, I want `ReportServiceImplDatabaseTest` to verify report lifecycle with real database repositories, so that report use cases work with PostgreSQL/MySQL
54. As a developer, I want `ReportServiceImplDatabaseTest` to test report image attachment, so that image associations work end-to-end

### Category Service Tests

55. As a developer, I want `CategoryServiceImplInMemoryTest` to verify category listing, so that all categories are returned
56. As a developer, I want `CategoryServiceImplInMemoryTest` to test category retrieval by ID, so that individual category fetch works
57. As a developer, I want `CategoryServiceImplDatabaseTest` to verify category repository integration, so that category use cases work with PostgreSQL/MySQL

### Job Scheduler Service Tests

58. As a developer, I want `JobSchedulerServiceImplInMemoryTest` to verify start auction job scheduling, so that auction start automation works
59. As a developer, I want `JobSchedulerServiceImplInMemoryTest` to test end auction job scheduling, so that auction end automation works
60. As a developer, I want `JobSchedulerServiceImplInMemoryTest` to test cancel auction job scheduling, so that auction cancellation automation works
61. As a developer, I want `JobSchedulerServiceImplInMemoryTest` to verify job deletion on auction state change, so that job cleanup works
62. As a developer, I want `JobSchedulerServiceImplInMemoryTest` to verify Quartz job data map contains auction ID, so that job context is correct
63. As a developer, I want `JobSchedulerServiceImplDatabaseTest` to verify job scheduling with real Quartz Scheduler, so that scheduling integration works

## Implementation Decisions

### Modules to Build/Modify

**Test Infrastructure Module** (`application/test` package) — Test utilities that enable dependency inversion in tests:
- `TestRepositoryFactory` — Static factory providing in-memory implementations for all domain repository interfaces (AuctionRepository, BidRepository, UserRepository, ItemRepository, NotificationRepository, ReportRepository, ReportResultRepository, CategoryRepository, WinnerRepository, VerificationTokenRepository)
- `TestPortFactory` — Static factory providing in-memory implementations for all port interfaces (JwtPort, MailPort, WebSocketPort, ImageStoragePort) plus EventPublisher
- `BaseInMemoryTest` — Abstract JUnit 5 base class that provides factory-accessed dependencies and can be extended by all in-memory test classes

**In-Memory Implementations Module** (`application/impl/inmemory` package) — Existing test doubles extended with:
- `InMemoryScheduler` — NEW: In-memory implementation of Quartz Scheduler interface for testing JobSchedulerService

**Service Test Modules** (`application/impl` package) — 16 test classes (8 services × 2 variants):
- `AuthServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `AuthServiceImplDatabaseTest` — Integration tests with database repositories
- `AuctionServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `AuctionServiceImplDatabaseTest` — Integration tests with database repositories
- `BidServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `BidServiceImplDatabaseTest` — Integration tests with database repositories
- `ItemServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `ItemServiceImplDatabaseTest` — Integration tests with database repositories
- `NotificationServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `NotificationServiceImplDatabaseTest` — Integration tests with database repositories
- `ReportServiceImplInMemoryTest` — Unit tests with in-memory repositories/ports
- `ReportServiceImplDatabaseTest` — Integration tests with database repositories
- `CategoryServiceImplInMemoryTest` — NEW: Unit tests for CategoryService
- `CategoryServiceImplDatabaseTest` — NEW: Integration tests for CategoryService
- `JobSchedulerServiceImplInMemoryTest` — NEW: Unit tests for JobSchedulerService
- `JobSchedulerServiceImplDatabaseTest` — NEW: Integration tests for JobSchedulerService

### Interfaces Preserved

All existing domain repository interfaces remain unchanged:
- `AuctionRepository`, `BidRepository`, `WinnerRepository` (Auction context)
- `UserRepository`, `VerificationTokenRepository` (Auth context)
- `ItemRepository`, `ImageStoragePort` (Item context)
- `NotificationRepository`, `WebSocketPort` (Notification context)
- `ReportRepository`, `ReportResultRepository`, `ReportImageRepository` (Report context)
- `CategoryRepository` (Auction context)

All existing application port interfaces remain unchanged:
- `JwtPort`, `MailPort` (Auth context)

All existing domain event interfaces remain unchanged:
- `EventPublisher`

### Technical Clarifications

1. **Dependency Injection in Tests** — In-memory tests use factory pattern for manual dependency provision; database tests use Spring `@Autowired` on interfaces
2. **Service Construction** — Both test types manually construct service implementations with provided dependencies (no `@Autowired` on services in tests)
3. **Database Test Isolation** — Database tests extend `BaseIntegrationTest` which truncates all tables after each test method via `@AfterEach`
4. **Test Profile** — Database tests use `@ActiveProfiles("test")` with existing `application-test.properties` MySQL configuration
5. **Test Naming Convention** — `<ServiceName>Impl<Variant>Test.java` where Variant is `InMemory` or `Database`

### Architectural Decisions

1. **No Mocking Frameworks** — Consistent with existing hexagonal architecture decision (ADR-0011) to use real in-memory implementations instead of Mockito
2. **Separation of Test Concerns** — Unit tests (in-memory) verify business logic in isolation; integration tests (database) verify persistence integration
3. **Interface-Based Testing** — All tests depend only on repository/port interfaces, enabling easy swapping of implementations
4. **Manual Service Construction** — Tests explicitly construct services with dependencies, making test intent clear and avoiding Spring context for unit tests
5. **Factory Pattern for Test Dependencies** — Centralized provision of in-memory implementations reduces boilerplate and ensures consistency

## Testing Decisions

### What Makes a Good Test

1. **Test External Behavior Only** — Tests verify service method contracts (input → output → side effects), not implementation details
2. **Focus on Domain Invariants** — Tests ensure business rules are enforced (e.g., bid must be ≥ start price + step)
3. **Verify State Transitions** — Tests confirm entity state machines work correctly (e.g., Auction DRAFT → PENDING → QUEUED)
4. **Check Event Publication** — Tests verify domain events are published at appropriate times
5. **Validate Authorization Rules** — Tests ensure owner/admin permissions are enforced

### Modules to be Tested

**Domain Layer** — Indirectly tested through application service tests (domain behavior is exercised via service methods)

**Application Layer** — Directly tested:
- All 8 application service implementations with both in-memory and database variants
- Service orchestration of domain logic
- Input validation and error handling

**Adapter Layer** — Indirectly tested through integration tests (JPA repository implementations are exercised via database tests)

### Prior Art for Tests

The codebase already follows these testing patterns from the hexagonal DDD refactoring (ADR-0011, PRD-0002):
- In-memory implementations for all repository interfaces (existing in `application/impl/inmemory`)
- In-memory implementations for port interfaces (existing: InMemoryJwtPort, InMemoryMailPort, InMemoryWebSocketPort, InMemoryImageStoragePort, InMemoryEventPublisher)
- `BaseIntegrationTest` with automatic database cleanup via `truncateAllTables()`
- Testcontainers MySQL configuration via `application-test.properties`
- Manual service construction in existing tests (pattern to be preserved and enhanced)

## Out of Scope

1. **Domain Entity Unit Tests** — Domain model behavior is already covered by existing domain tests; this PRD focuses on application service tests
2. **REST Controller Tests** — Controller integration tests are out of scope; this PRD focuses on service layer testing
3. **Testcontainers Configuration** — Existing MySQL Testcontainers setup in `application-test.properties` will be reused, not modified
4. **CI/CD Pipeline Changes** — Test execution in CI/CD remains unchanged; new tests will run as part of existing test suites
5. **Production Code Refactoring** — Application service implementations and domain models will not be modified; only test code changes
6. **Mockito Introduction** — No mocking frameworks will be added; in-memory implementations are sufficient
7. **Test Coverage Reporting** — Coverage configuration and reporting remain unchanged
8. **Spring Boot Test Annotations** — Beyond `@SpringBootTest` and `@Autowired`, no new Spring test annotations will be introduced

## Further Notes

### Deep Modules Identified

The refactoring exposes several deep modules in the hexagonal architecture that are naturally testable in isolation:

1. **Domain Services** — `BiddingRulesService`, `AuctionLifecycleService`, `LegitimateScoreService` encapsulate complex business rules that span multiple aggregates
2. **Repository Ports** — Repository interfaces define clear contracts for persistence without implementation details
3. **Application Services** — Service implementations orchestrate domain logic and can be tested with any repository/port implementation
4. **Port Interfaces** — External service contracts (JwtPort, MailPort, etc.) enable testing without real infrastructure

### Testing Strategy Rationale

The two-test-variant approach provides:
- **Fast feedback** — In-memory unit tests run in milliseconds without database dependencies
- **High confidence** — Database integration tests verify real persistence behavior
- **Clear intent** — Separation makes it obvious what each test verifies
- **Maintainability** — Changes to in-memory implementations don't affect database tests and vice versa

### Migration Path

1. Create test infrastructure (factories, base classes, InMemoryScheduler)
2. Rename existing tests to `*InMemoryTest` and refactor to use factories
3. Create new `*DatabaseTest` classes for all 8 services
4. Verify all tests pass
5. Clean up any redundant code

### Related Documentation

- ADR-0011: Hexagonal DDD Refactoring (establishes architecture principles)
- CONTEXT-MAP.md: Bounded context relationships
- Existing test infrastructure in `application/impl/inmemory/`
- `BaseIntegrationTest.java` for database test patterns
