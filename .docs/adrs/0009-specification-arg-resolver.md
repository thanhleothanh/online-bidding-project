# ADR-0009: Specification-Arg-Resolver for Dynamic Queries

## Status

Accepted

## Context

The admin-facing APIs for auctions, profiles, and reports require dynamic filtering:

- Filter auctions by status, price range, time range, highest price
- Filter profiles by status
- Filter reports by judgment result

Building these dynamic queries with `@RequestParam` and manual `Specification` construction is verbose and error-prone. Each new filter parameter requires boilerplate code.

## Decision

Use `net.kaczmarzyk:specification-arg-resolver:3.1.0` for declarative dynamic queries:

1. Add the dependency to `pom.xml`
2. Use `@And` and `@Spec` annotations on controller method parameters:
   ```java
   @And({
       @Spec(path = "status", params = "status", spec = Equal.class),
       @Spec(path = "priceStart", params = "priceStartGt", spec = GreaterThan.class),
       @Spec(path = "priceStart", params = "priceStartLt", spec = LessThan.class),
   })
   Specification<Auction> spec
   ```
3. Use `@Join` for cross-table filtering (e.g., filtering reports by `reportResult.result`)
4. Combine with Spring Data `Specification` repository methods

## Consequences

- **Positive**: Declarative filters reduce boilerplate; each filter is a single annotation
- **Positive**: Supports complex operations (Equal, GreaterThan, LessThan, Like) out of the box
- **Positive**: `@Join` enables filtering across entity relationships
- **Negative**: Annotation-heavy; complex filters are harder to read than programmatic specifications
- **Negative**: Third-party dependency that must be maintained
- **Neutral**: Works with Spring Data JPA repositories without additional configuration
