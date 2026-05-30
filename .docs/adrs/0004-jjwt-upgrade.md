# ADR-0004: JJWT 0.12.x Upgrade

## Status

Accepted

## Context

The project used `io.jsonwebtoken:jjwt:0.9.1` for JWT generation and validation. Version 0.9.1 uses deprecated APIs:

- `Jwts.parser()` with `setSigningKey(String)` is deprecated
- `parseClaimsJws()` is deprecated in favor of `parseSignedClaims()`
- String-based algorithm selection (`SignatureAlgorithm.HS256`) is deprecated in favor of `SecretKey`

JJWT 0.12.x introduced breaking API changes while improving security defaults.

## Decision

Upgrade jjwt from 0.9.1 to 0.12.5 with the new API:

1. Add three jjwt dependencies: `jjwt-api:0.12.5`, `jjwt-impl:0.12.5` (runtime), `jjwt-jackson:0.12.5` (runtime)
2. Refactor `JwtUtils.java`:
   - Use `Jwts.parserBuilder().verifyWith(key).build()` instead of `Jwts.parser().setSigningKey(string)`
   - Use `.parseSignedClaims(token)` instead of `.parseClaimsJws(token)`
   - Use `SecretKey` via `Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))` for signing
   - Use `.subject()`, `.issuedAt()`, `.expiration()` builder methods
3. Preserve the same public method signatures: `generateTokenFromUsername()`, `validateJwtToken()`, `getUserNameFromJwtToken()`

## Consequences

- **Positive**: Uses non-deprecated, actively maintained API
- **Positive**: `SecretKey`-based signing is more secure than string-based algorithm selection
- **Positive**: Better Jackson integration for claims serialization
- **Negative**: Breaking API change requires updating all JWT-related code
- **Neutral**: JWT token format and cookie-based transport remain unchanged
