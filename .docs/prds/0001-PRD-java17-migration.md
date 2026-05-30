# PRD: Java 17 / Spring Boot 3.2 Migration + Documentation

## Problem Statement

The online bidding project is built on Java 11 and Spring Boot 2.7.1, which reached end of support in November 2023. The codebase uses the `javax.*` namespace which is incompatible with the Jakarta EE 10 ecosystem that all modern Spring libraries target. Key dependencies are pinned to outdated versions (Spring Security 5.2.1, Springfox 2.9.2, jjwt 0.9.1) that have known security vulnerabilities and lack support for current Java features. The project has zero documentation — no README, no architecture decision records, no domain context maps — making onboarding and long-term maintenance difficult. There is also no dockerized local development environment, requiring developers to manually configure MySQL and other services.

The codebase also contains modernization debt: field injection via `@Autowired` throughout (94 instances), `e.printStackTrace()` instead of proper logging (10 instances), DTOs that could be immutable records, and multi-line string concatenation that could use text blocks.

## Solution

Migrate the entire codebase from Java 11 / Spring Boot 2.7.1 to Java 17 / Spring Boot 3.2.x in a single atomic change (the javax → jakarta namespace migration is not backward-compatible and cannot be done incrementally). Simultaneously modernize code patterns to use idiomatic Java 17 features. Establish a complete documentation suite including README, context maps, context files for each bounded context, and ADRs for every significant architectural decision. Provide a docker-compose environment for zero-config local development.

## User Stories

### Infrastructure & Build

1. As a developer, I want to run `docker-compose up` and have a fully configured local MySQL instance with schema and seed data, so that I can start developing without manual database setup
2. As a developer, I want the Dockerfile to use a supported base image (Eclipse Temurin 17), so that the container build is reliable and security-patched
3. As a developer, I want the CI/CD pipeline to build with Java 17, so that merges are validated against the correct runtime
4. As a developer, I want the Maven build to target Java 17 source and target compatibility, so that I can use modern Java syntax in my code
5. As a developer, I want Spring Boot 3.2.x as the framework version, so that I have long-term support and access to current features

### javax → jakarta Migration

6. As a developer, I want all JPA entity imports changed from `javax.persistence` to `jakarta.persistence`, so that entities compile against Hibernate 6
7. As a developer, I want all servlet imports changed from `javax.servlet` to `jakarta.servlet`, so that filters and request handling compile against the Servlet 6 API
8. As a developer, I want all validation imports changed from `javax.validation` to `jakarta.validation`, so that bean validation works with Hibernate Validator 8.x
9. As a developer, I want all mail imports changed from `javax.mail` to `jakarta.mail`, so that email sending works with the Jakarta Mail API

### Spring Security 6 Rewrite

10. As a developer, I want the security configuration rewritten to use component-based `SecurityFilterChain` beans instead of the removed `WebSecurityConfigurerAdapter`, so that security is configured idiomatically for Spring Security 6
11. As a developer, I want `antMatchers` replaced with `requestMatchers`, so that URL matching uses the current API
12. As a developer, I want `@EnableGlobalMethodSecurity` replaced with `@EnableMethodSecurity`, so that method-level security uses the current annotation
13. As a developer, I want the security DSL to use lambda-style configuration instead of chained method calls, so that the configuration is readable and follows current best practices
14. As a developer, I want the pinned Spring Security 5.2.1 dependencies removed from pom.xml, so that Spring Boot 3 manages the correct Security 6.x version

### Swagger / OpenAPI

15. As a developer, I want Springfox removed and replaced with springdoc-openapi, so that API documentation works with Spring Boot 3
16. As a developer, I want the Swagger UI accessible at `/swagger-ui/index.html`, so that I can test API endpoints in the browser
17. As a developer, I want the OpenAPI spec available at `/v3/api-docs`, so that clients can generate code from the API definition

### JWT

18. As a developer, I want jjwt upgraded to 0.12.x with the new API (`parserBuilder`, `parseSignedClaims`, `SecretKey`), so that JWT operations use the current, non-deprecated API
19. As a developer, I want JWT signing to use `SecretKey` instead of raw string algorithms, so that token security follows current best practices

### Thymeleaf & Email

20. As a developer, I want Thymeleaf template engine imports changed from `spring5` to `spring6` packages, so that email template rendering works with Spring Boot 3

### Hibernate & JPA

21. As a developer, I want the deprecated `MySQL5InnoDBDialect` removed from application properties, so that Hibernate 6 auto-detects the correct dialect

### Exception Handling

22. As a developer, I want the `CustomExceptionHandler` method signatures updated to use `HttpStatusCode` instead of `HttpStatus`, so that the exception handler compiles against Spring MVC 6

### Code Modernization

23. As a developer, I want eligible response DTOs converted to Java records, so that data carriers are immutable and concise
24. As a developer, I want multi-line SQL strings in `AuctionDao` converted to text blocks, so that embedded SQL is readable
25. As a developer, I want all `e.printStackTrace()` calls replaced with SLF4J logger output, so that errors are properly logged with context
26. As a developer, I want field injection via `@Autowired` replaced with constructor injection throughout all services and controllers, so that dependencies are explicit and testable
27. As a developer, I want the `org.jetbrains:annotations` dependency pinned to a specific version instead of `RELEASE`, so that builds are reproducible

### Documentation — README

28. As a developer, I want a README.md with project overview, tech stack, and architecture summary, so that new contributors understand the project quickly
29. As a developer, I want the README to include docker-compose setup instructions, so that I can spin up a local environment with one command
30. As a developer, I want the README to document the API endpoints and their purposes, so that frontend developers know what's available
31. As a developer, I want the README to explain the auction lifecycle state machine, so that the core business flow is documented

### Documentation — Context Map

32. As a developer, I want a context map showing all bounded contexts and their relationships, so that I understand the domain boundaries
33. As a developer, I want each bounded context documented with its domain model, invariants, and integration points, so that I can work within a context without breaking others

### Documentation — ADRs

34. As a developer, I want an ADR documenting the Spring Boot 2.7 → 3.2 migration decision, including the javax → jakarta trade-offs, so that future readers understand why this breaking change was made
35. As a developer, I want an ADR documenting the Spring Security 6 component-based configuration decision, so that the security architecture is explained
36. As a developer, I want an ADR documenting the Springfox → springdoc-openapi replacement, so that the API documentation toolchain choice is recorded
37. As a developer, I want an ADR documenting the jjwt upgrade and SecretKey-based signing, so that the JWT strategy is clear
38. As a developer, I want an ADR documenting why Quartz was chosen over MySQL event scheduler for job scheduling, so that the scheduling architecture rationale is preserved
39. As a developer, I want an ADR documenting the Cloudinary choice for image storage, so that the file storage strategy is explained
40. As a developer, I want an ADR documenting the STOMP WebSocket choice for real-time bidding, so that the real-time architecture is clear
41. As a developer, I want an ADR documenting the table-per-type inheritance model for Profile/User/Admin, so that the entity design rationale is recorded
42. As a developer, I want an ADR documenting the specification-arg-resolver choice for dynamic filtering, so that the query-building strategy is explained
43. As a developer, I want an ADR documenting the docker-compose local development setup, so that the infrastructure decision is recorded

## Implementation Decisions

### Modules to Modify

**Infrastructure Module** — Dockerfile, docker-compose.yml, CI/CD pipeline, Maven build. This is the foundation; everything else depends on it being correct.

**Persistence Module** — All entity classes, repositories, converters, and DAO. The javax → jakarta migration touches every file in this module. This is the largest single change by file count (~40 files).

**Security Module** — WebSecurityConfiguration, AuthenticationTokenFilter, AuthenticationEntryPointJwt, UserDetailsImpl, JwtUtils. The Spring Security 5 → 6 rewrite is the most architecturally significant change. The `WebSecurityConfigurerAdapter` removal requires a fundamentally different configuration approach.

**API Documentation Module** — SwaggerConfig replaced entirely. Springfox is incompatible with Spring Boot 3; springdoc-openapi is the replacement. The configuration interface changes from `Docket` to `OpenAPI` bean.

**Exception Handling Module** — CustomExceptionHandler. Method signatures changed in Spring MVC 6 (`HttpStatus` → `HttpStatusCode`). All 9 `@ExceptionHandler` methods need signature updates.

**Email Module** — MailServiceImpl, ThymeleafTemplateConfiguration. Package rename from `spring5` to `spring6` for Thymeleaf integration.

**Application Properties** — Hibernate dialect removal, any Boot 3-specific config changes.

**Code Quality Module** — Cross-cutting changes: field → constructor injection (94 instances across all services/controllers), `e.printStackTrace()` → SLF4J (10 instances), DTO → record conversions, text blocks.

**Documentation Module** — Entirely new. README.md, `.docs/contexts/` directory with context map and per-context files, `.docs/adrs/` directory with 10 ADRs.

### Interfaces Modified

- `SecurityFilterChain` bean replaces `WebSecurityConfigurerAdapter.configure()` — the entire security configuration surface changes
- `OpenAPI` bean replaces Springfox `Docket` — the API documentation configuration interface changes
- `JwtUtils` internal API changes — method signatures updated to jjwt 0.12 patterns, but the external interface (public methods called by other modules) stays the same
- `CustomExceptionHandler` overridden method signatures change — the handler interface is the same but parameter types shift from `HttpStatus` to `HttpStatusCode`
- All service and controller constructors change — moving from no-arg + field injection to constructor injection means every `@Service` and `@Controller` class gets an explicit constructor

### Schema Changes

None. The database schema remains identical. The `javax.persistence` → `jakarta.persistence` migration is a namespace change only; JPA annotations map to the same database columns.

### API Contracts

None. The REST API paths, request/response formats, and behavior remain identical. The only change is the Swagger UI path (`/swagger-ui.html` → `/swagger-ui/index.html`) and the OpenAPI spec path (`/v2/api-docs` → `/v3/api-docs`).

### Architectural Decisions

1. **Big-bang migration** — The javax → jakarta change cannot be done incrementally. All phases (infrastructure, namespace migration, dependency rewrites, modernization) land as one atomic change.
2. **Spring Boot 3.2.x** — Chosen over 3.3.x for stability; 3.2 is the current LTS line.
3. **No database migration tool** — Docker-compose init script approach. Flyway/Liquibase adds scope without proportional value for a course project.
4. **Constructor injection over field injection** — Industry standard, improves testability, makes dependencies explicit.
5. **Records for response DTOs only** — Request DTOs need setters for deserialization and validation; response DTOs that are purely read can be records.

## Testing Decisions

**No tests will be written as part of this migration.** The project currently has zero tests and the `src/test/` directory contains no test files. Adding tests is a separate effort.

What makes a good test (for future reference): Tests should verify external behavior (API responses, database state changes, security access rules) rather than implementation details (internal method calls, private state). Integration tests that boot the full Spring context and hit the actual endpoints would provide the most value for this project, given its layered architecture and heavy use of Spring auto-configuration.

Modules that would benefit most from future testing:
- Security configuration (access control rules, JWT validation, role-based authorization)
- Auction lifecycle state machine (state transitions, Quartz job triggers, winner determination)
- Bidding service (concurrent bid handling, minimum bid enforcement, WebSocket broadcast)
- Notification service (event-driven notification creation and delivery)

## Out of Scope

- **Tests** — No unit or integration tests will be written. The migration is large enough without adding test authoring.
- **Flyway/Liquibase** — Database migration tooling is not added. The existing SQL init script approach is retained.
- **API contract changes** — No new endpoints, no changed response formats, no versioned API.
- **Database schema changes** — The schema is untouched. Only the JPA namespace changes.
- **Frontend changes** — This is a backend-only migration. The WebSocket client, email templates, and any frontend consumers are not modified (except the Swagger UI path change, which is automatic).
- **Performance optimization** — No query optimization, caching, or connection pooling changes. These are separate concerns.
- **Microservice decomposition** — The monolithic architecture is preserved. This migration does not introduce service boundaries.
- **CI/CD pipeline changes beyond Java version** — No new stages, no deployment changes, no infrastructure-as-code.

## Further Notes

### Migration Risk Assessment

The highest-risk changes are:
1. **Spring Security 6 rewrite** — The security configuration is the most architecturally complex change. A misconfiguration here could lock users out or expose protected endpoints. Manual testing of all auth flows is critical after migration.
2. **jjwt upgrade** — Token generation/validation must produce identical tokens or existing sessions break. The `SecretKey` migration changes the signing mechanism.
3. **Swagger replacement** — The API documentation path changes; any external clients hitting `/swagger-ui.html` will get 404s until they update to `/swagger-ui/index.html`.

The lowest-risk changes are:
1. **javax → jakarta imports** — Mechanical find-and-replace. The namespace mapping is 1:1.
2. **Text blocks** — Purely cosmetic. No behavioral change.
3. **Constructor injection** — Same behavior, different wiring mechanism.

### Execution Strategy

All changes in Phases 1-4 (infrastructure, namespace migration, dependency rewrites, code modernization) must be committed together. The javax → jakarta change is not backward-compatible; a partial commit will not compile.

Phase 5 (documentation) can be a separate commit but should follow immediately.

### Post-Migration Verification

After the atomic commit, the application should:
1. `mvn clean compile` succeeds with Java 17
2. `docker-compose up` starts MySQL and the application connects
3. Swagger UI loads at `/swagger-ui/index.html`
4. Login/signup flows work (JWT creation and validation)
5. Auction CRUD works (create, submit, approve, bid, end)
6. WebSocket connection establishes for real-time bidding
7. Email verification sends and verifies correctly
