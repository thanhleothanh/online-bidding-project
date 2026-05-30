# ADR-0002: Spring Security 6 Rewrite

## Status

Accepted

## Context

Spring Security 6 (bundled with Spring Boot 3.x) removed several deprecated APIs:

- `WebSecurityConfigurerAdapter` is removed; configuration must use component-based `SecurityFilterChain` `@Bean` methods
- `antMatchers()` is removed in favor of `requestMatchers()`
- `@EnableGlobalMethodSecurity` is replaced by `@EnableMethodSecurity`
- Lambda DSL is the recommended way to configure `HttpSecurity`

The original `WebSecurityConfiguration` extended `WebSecurityConfigurerAdapter` and used the deprecated APIs. The pinned `spring-security-web:5.2.1.RELEASE` and `spring-security-config:5.2.1.RELEASE` dependencies were incompatible with Spring Boot 3.x.

## Decision

Rewrite `WebSecurityConfiguration` to use the Spring Security 6 component-based approach:

1. Remove `extends WebSecurityConfigurerAdapter`
2. Add `@Bean SecurityFilterChain filterChain(HttpSecurity http)` method using lambda DSL
3. Replace all `antMatchers()` calls with `requestMatchers()`
4. Replace `@EnableGlobalMethodSecurity` with `@EnableMethodSecurity(prePostEnabled = true)`
5. Remove the pinned `spring-security-web:5.2.1.RELEASE` and `spring-security-config:5.2.1.RELEASE` dependencies from `pom.xml`
6. Preserve existing CORS configuration, CSRF disabled, session management (STATELESS), JWT entry point, and authentication filter registration

## Consequences

- **Positive**: Compatible with Spring Boot 3.x and Spring Security 6
- **Positive**: Lambda DSL provides type-safe configuration and better IDE support
- **Positive**: No more pinned legacy security dependencies
- **Negative**: Must rewrite configuration when upgrading; can no longer extend `WebSecurityConfigurerAdapter` as a shortcut
- **Neutral**: Functional behavior remains identical; same whitelist, same filter chain, same CORS policy
