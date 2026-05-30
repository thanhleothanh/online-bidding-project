# ADR-0010: Docker-Compose for Local Development

## Status

Accepted

## Context

The application depends on MySQL 8 for data storage. Developers need a consistent, reproducible local development environment that:

- Matches the production database version
- Initializes the schema automatically
- Starts quickly and tears down cleanly
- Does not require manual MySQL installation

## Decision

Use docker-compose with MySQL 8:

1. Create `docker-compose.yml` with a MySQL 8 service:
   - Port mapping: `3306:3306`
   - Volume mount for init script: `./mysql/onlineBiddingProject.sql:/docker-entrypoint-initdb.d/init.sql`
   - Persistent data volume: `mysql-data:/var/lib/mysql`
   - Health check: `mysqladmin ping -h localhost`
   - Environment variables matching `application.properties` defaults
2. Create `Dockerfile` for the application using `eclipse-temurin:17-jre`
3. Schema initialization script (`mysql/onlineBiddingProject.sql`) runs automatically on first `docker-compose up`

### Usage

```bash
# Start MySQL
docker-compose up -d

# Verify MySQL is ready
docker-compose logs mysql | grep "ready for connections"

# Run the application
mvn spring-boot:run

# Stop and clean up
docker-compose down
```

## Consequences

- **Positive**: Zero-setup local development; `docker-compose up` is the only prerequisite
- **Positive**: Schema is initialized automatically from version-controlled SQL
- **Positive**: Persistent volumes preserve data between restarts
- **Positive**: Health check ensures MySQL is ready before application starts
- **Negative**: Docker required on development machines
- **Negative**: Port 3306 may conflict with existing MySQL installations
- **Neutral**: Environment variables in `docker-compose.yml` match `application.properties` defaults; can be overridden for custom setups
