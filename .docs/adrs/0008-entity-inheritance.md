# ADR-0008: Entity Inheritance with Shared Primary Key

## Status

Accepted

## Context

The Auth context has a `Profile` entity that serves as the base for both regular users (`User`) and administrators (`Admin`). These entity types share most fields (username, email, password, name, status, role) but have type-specific behavior:

- `User` has a legitimate score and participates in auctions
- `Admin` judges reports and approves auctions

The system needs to distinguish between user types for authorization (`@PreAuthorize("hasAuthority('USER')")`) while sharing the common profile data in a single table.

## Decision

Use JPA single-table inheritance with shared primary key:

1. `Profile` is the base `@Entity` with `@Inheritance(strategy = InheritanceType.JOINED)` or single-table strategy
2. `User` extends `Profile` with `@PrimaryKeyJoinColumn(name = "id")`
3. `Admin` extends `Profile` with `@PrimaryKeyJoinColumn(name = "id")`
4. `Role` entity determines whether a profile is USER or ADMIN
5. The `Role` is stored in the `profile` table as a foreign key
6. `UserDetailsImpl.build(Profile)` constructs Spring Security principal from profile data

## Consequences

- **Positive**: Single table for all profiles simplifies queries and joins
- **Positive**: Shared fields (username, email, password) are not duplicated
- **Positive**: Type-safe authorization via `instanceof User` / `instanceof Admin`
- **Negative**: Adding type-specific fields requires altering the base table (nullable columns for fields that only apply to one subtype)
- **Negative**: Cannot have non-nullable type-specific columns without NULL values for other subtypes
- **Neutral**: `User` and `Admin` entities are lightweight; most logic lives in services
