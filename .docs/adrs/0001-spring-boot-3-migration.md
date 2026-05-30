# ADR-0001: Spring Boot 3.2 Migration

## Status

Accepted

## Context

The project was originally built on Spring Boot 2.x with Java 11. Spring Boot 2.x reached end of support in November 2023. The codebase needed to migrate to Spring Boot 3.2 to receive security patches, performance improvements, and Java 17 support. Spring Boot 3.x requires Jakarta EE 9+ (jakarta.* namespace), Java 17+, and uses Hibernate 6.

## Decision

Migrate the project to Spring Boot 3.2.5 with Java 17. The migration covers:

1. Update `spring-boot-starter-parent` to 3.2.5 in `pom.xml`
2. Set `java.version` property to 17
3. Rename all `javax.*` imports to `jakarta.*` across the codebase (persistence, servlet, validation, mail)
4. Update `mysql-connector-j` (replacing `mysql-connector-java`)
5. Replace `MySQL5InnoDBDialect` with `MySQLDialect` in `application.properties`
6. Update the Dockerfile base image from `openjdk:11` to `eclipse-temurin:17-jre`
7. Update CI/CD pipeline to use `java-version: '17'`

## Consequences

- **Positive**: Access to Java 17 features (records, text blocks, sealed classes, pattern matching), Spring Boot 3.2 performance improvements, ongoing security support
- **Positive**: Hibernate 6 with improved SQL generation and Jakarta Persistence 3.1 support
- **Negative**: Breaking change requiring all `javax.*` imports to be renamed; some third-party libraries may not yet support Jakarta EE 9
- **Neutral**: All `@Column`, `@Table`, `@Entity` annotations stay the same; only the import package changes
