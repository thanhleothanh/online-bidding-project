# ADR-0003: Springdoc-OpenAPI replaces Springfox

## Status

Accepted

## Context

The project used Springfox Swagger 2.9.2 for API documentation. Springfox is incompatible with Spring Boot 3.x because:

- Springfox relies on internal Spring MVC APIs that changed in Spring Framework 6
- `@EnableSwagger2` does not work with Spring Boot 3.x
- Springfox uses `antMatchers`-style path matching which conflicts with the new default `PathPatternParser`

The Springfox project has been effectively abandoned (last release in 2020). The community standard for Spring Boot 3.x is springdoc-openapi.

## Decision

Replace Springfox with springdoc-openapi-starter-webmvc-ui:

1. Remove `springfox-swagger2` and `springfox-swagger-ui` dependencies from `pom.xml`
2. Add `springdoc-openapi-starter-webmvc-ui:2.3.0` dependency
3. Rewrite `SwaggerConfig.java` to use an `OpenAPI` `@Bean` instead of Springfox `Docket`
4. Remove `@EnableSwagger2` annotation
5. Update the security whitelist in `WebSecurityConfiguration` to include `/swagger-ui/**` and `/v3/api-docs/**`
6. Remove `spring.mvc.pathmatch.matching-strategy=ant-path-matcher` from `application.properties` (no longer needed)

## Consequences

- **Positive**: Full compatibility with Spring Boot 3.x
- **Positive**: springdoc generates OpenAPI 3.0 spec (vs Swagger 2.0 with Springfox)
- **Positive**: Swagger UI accessible at `/swagger-ui/index.html`, OpenAPI spec at `/v3/api-docs`
- **Positive**: Active maintenance and community support
- **Negative**: API documentation URL changes from `/swagger-ui.html` to `/swagger-ui/index.html`
- **Neutral**: Security whitelist updated to cover new paths
