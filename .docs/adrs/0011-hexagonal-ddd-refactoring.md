# ADR-0011: Hexagonal DDD Refactoring

## Status

Accepted

## Context

The original monolithic architecture with traditional layered architecture (controllers → services → repositories) had several issues:

1. **Tight coupling** between business logic and infrastructure concerns
2. **Hard to test** due to Spring context dependencies in unit tests
3. **Mixed responsibilities** in service layer handling both business rules and infrastructure concerns
4. **No clear boundaries** between bounded contexts
5. **Framework dependencies** throughout the codebase making refactoring difficult

## Decision

We will refactor the codebase to **Hexagonal Architecture (Ports & Adapters)** with **Domain-Driven Design** principles:

### Architecture Structure

- **Domain Layer**: Pure business logic with entities, value objects, and domain services
- **Application Layer**: Use case orchestration with application services
- **Adapter Layer**: Driving adapters (REST controllers) and driven adapters (database, external services)
- **Infrastructure Layer**: Cross-cutting concerns (security, configuration, utilities)

### Key Design Decisions

#### 1. Separate Domain and Persistence Models
- **Decision**: Domain entities are separate from JPA entities
- **Trade-off**: More code (mappers), but cleaner domain models without framework annotations
- **Benefit**: Domain models can be tested without Spring context

#### 2. Manual Mappers vs ModelMapper
- **Decision**: Use static utility mappers instead of ModelMapper
- **Trade-off**: More boilerplate code, but explicit mapping logic
- **Benefit**: No reflection overhead, compile-time safety, easier debugging

#### 3. Static Mappers vs Spring Beans
- **Decision**: Use static mappers instead of Spring-managed beans
- **Trade-off**: No dependency injection in mappers, but simpler and faster
- **Benefit**: No Spring context needed for domain/application tests

#### 4. In-Memory Implementations vs Mockito
- **Decision**: Use in-memory implementations for testing instead of Mockito
- **Trade-off**: More code to maintain, but more realistic test behavior
- **Benefit**: Tests are more reliable and easier to understand

#### 5. Removed specification-arg-resolver
- **Decision**: Remove specification-arg-resolver dependency
- **Trade-off**: Need to implement query building manually
- **Benefit**: Fewer dependencies, more control over query logic

### Migration Strategy

We used **context-by-context vertical slices** migration:

1. **Infrastructure Foundation** (Issue #1): Package structure, shared types, test infrastructure
2. **Auth Context** (Issues #2-4): Domain model, application services, adapters
3. **Item Context** (Issue #5): Complete vertical slice with all layers
4. **Auction Context** (Issues #6-7): Complex domain with state machine and events
5. **Report Context** (Issue #8): Cross-aggregate domain services
6. **Notification Context** (Issue #9): Event-driven architecture with WebSocket
7. **Admin Context** (Issue #10): Admin controllers and orchestration
8. **Cleanup & Verification** (Issue #11): Legacy removal and validation

## Consequences

### Positive

1. **Testability**: Domain and application layers can be tested without Spring context
2. **Maintainability**: Clear separation of concerns and bounded contexts
3. **Flexibility**: Easy to swap adapters (e.g., change database, add new API)
4. **Domain Focus**: Business logic is isolated and easier to understand
5. **Parallel Development**: Teams can work on different contexts independently

### Negative

1. **More Code**: Separate models, mappers, and port interfaces increase codebase
2. **Learning Curve**: Team needs to understand hexagonal architecture principles
3. **Initial Complexity**: More files and packages to navigate
4. **Mapping Overhead**: Manual mapping between domain and persistence models

### Mitigations

1. **Code Generation**: Consider generating mappers and boilerplate code
2. **Documentation**: Comprehensive ADRs and context maps
3. **Training**: Team workshops on hexagonal architecture
4. **Tooling**: IDE support for navigation and refactoring

## Alternatives Considered

### 1. Keep Traditional Layered Architecture
- **Pros**: Familiar to team, less code
- **Cons**: Doesn't solve the original problems, tight coupling remains

### 2. Use Clean Architecture
- **Pros**: Similar benefits to hexagonal architecture
- **Cons**: More prescriptive, less flexibility in adapter placement

### 3. Use Microservices
- **Pros**: Independent deployment, technology diversity
- **Cons**: Operational complexity, network latency, data consistency challenges

## References

- Hexagonal Architecture by Alistair Cockburn
- Domain-Driven Design by Eric Evans
- Spring Boot Documentation
- Testcontainers Documentation

## Related ADRs

- ADR-0001: Spring Boot 3 Migration
- ADR-0002: Spring Security 6 Rewrite
- ADR-0008: Entity Inheritance Strategy
- ADR-0010: Docker Compose Setup