# Online Bidding Project

A full-stack online auction/bidding platform built with Spring Boot 3.2 and Java 17.

## Tech Stack

| Layer | Technology |
|---|---|
| Runtime | Java 17 (Eclipse Temurin) |
| Framework | Spring Boot 3.2.5 |
| Security | Spring Security 6 (JWT cookie-based) |
| Database | MySQL 8 (Hibernate 6 / JPA) |
| Scheduler | Quartz (JDBC job store) |
| Real-time | Spring WebSocket + STOMP |
| Email | Spring Mail + Thymeleaf |
| Image Storage | Cloudinary |
| API Docs | springdoc-openapi (Swagger UI) |
| ORM | ModelMapper |
| Query Specs | specification-arg-resolver |
| Build | Maven |
| Containerization | Docker + docker-compose |

## Architecture Summary

The project follows **Hexagonal Architecture (Ports & Adapters)** with Domain-Driven Design bounded contexts:

### Hexagonal Architecture Structure

```
src/main/java/com/ghtk/onlinebiddingproject/
├── adapter/                    # Adapters (driving & driven)
│   ├── in/                     # Driving adapters (REST controllers, WebSocket)
│   │   ├── admin/              # Admin REST controllers
│   │   ├── auth/               # Auth REST controllers
│   │   ├── auction/            # Auction REST controllers
│   │   ├── item/               # Item REST controllers
│   │   ├── notification/       # Notification REST controllers
│   │   └── report/             # Report REST controllers
│   └── out/                    # Driven adapters (database, external services)
│       ├── cloudinary/         # Cloudinary image storage adapter
│       ├── email/              # Email service adapter
│       ├── repository/         # Repository implementations
│       └── websocket/          # WebSocket adapter
├── application/                # Application services (use cases)
│   ├── impl/                   # Service implementations
│   └── port/                   # Application ports (interfaces)
├── domain/                     # Domain layer (business logic)
│   ├── event/                  # Domain events
│   ├── model/                  # Domain models (entities, value objects)
│   │   ├── auction/            # Auction context
│   │   ├── auth/               # Auth context
│   │   ├── item/               # Item context
│   │   ├── notification/       # Notification context
│   │   ├── report/             # Report context
│   │   └── shared/             # Shared value objects (Money)
│   ├── port/                   # Domain ports (interfaces)
│   └── service/                # Domain services
└── infrastructure/             # Infrastructure layer
    ├── config/                 # Configuration classes
    ├── events/                 # Event publisher implementation
    ├── quartz/                 # Quartz job scheduling
    ├── security/               # Security utilities
    ├── util/                   # Utility classes
    └── websocket/              # WebSocket configuration
```

### Bounded Contexts

The project has six core bounded contexts:

- **Auth** — Registration, login, JWT cookies, email verification
- **Auction** — Full auction lifecycle with Quartz-scheduled state transitions
- **Item** — Product CRUD with Cloudinary image storage
- **Report** — User-submitted reports with admin judgment workflow
- **Notification** — Real-time push notifications via WebSocket
- **Admin** — Auction approval, user management, report judgment

See `.docs/contexts/CONTEXT-MAP.md` for the full context map.

## Docker Compose Setup

### Prerequisites

- Docker and Docker Compose installed
- Port 3306 available

### Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd online-bidding-project
   ```

2. Start MySQL:
   ```bash
   docker-compose up -d
   ```

3. The database `onlineBiddingProject` will be created automatically with the schema from `mysql/onlineBiddingProject.sql`.

4. Configure environment variables (or use defaults in `application.properties`):
   ```bash
   export DB_URL=jdbc:mysql://localhost:3306/onlineBiddingProject
   export DB_USERNAME=thanh
   export DB_PASSWORD=Abc@12345
   ```

5. Build and run:
   ```bash
   mvn clean package -DskipTests
   java -jar target/*.jar
   ```

### Docker Build

```bash
mvn clean package -DskipTests
docker build -t online-bidding-api .
docker run -p 8080:8080 online-bidding-api
```

## API Endpoint Reference

### Auth (`/api/v1/auth`)

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/login` | No | Login with username/password |
| POST | `/signup` | No | Register new account |
| POST | `/logout` | No | Clear JWT cookie |
| GET | `/verificationSignup?token=` | No | Verify email address |

### Auctions (`/api/v1/auctions`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | No | List OPENING auctions (filterable, sortable, paginated) |
| GET | `/topTrending` | No | Top trending auctions by bid count |
| GET | `/{id}` | USER/ADMIN | Get auction details |
| POST | `/` | USER | Create new auction with item |
| PUT | `/{id}` | USER | Update draft/pending auction |
| PUT | `/{id}/submit` | USER | Submit auction for admin review |
| DELETE | `/{id}` | USER | Delete draft/pending auction |
| GET | `/{id}/bids` | USER/ADMIN | List bids for auction |
| POST | `/{id}/bids` | USER | Place a bid |

### Items (`/api/v1/items`)

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/` | USER | Create item |
| PUT | `/{id}` | USER | Update item |
| POST | `/{id}/itemImages` | USER | Add image to item |
| DELETE | `/{itemId}/itemImages/{id}` | USER | Delete item image |

### Profiles (`/api/v1/profiles`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/myProfile` | USER/ADMIN | Get current user profile |
| PUT | `/myProfile` | USER/ADMIN | Update profile |
| PUT | `/myProfile/changePassword` | USER/ADMIN | Change password |
| GET | `/myAuctions` | USER | List my auctions |
| GET | `/myNotifications` | USER/ADMIN | List my notifications |
| GET | `/myInterestedAuctions` | USER/ADMIN | List auctions I'm watching |
| POST | `/myInterestedAuctions` | USER | Start watching an auction |
| GET | `/{id}` | USER/ADMIN | Get profile by ID |
| GET | `/{id}/auctions` | USER/ADMIN | List auctions by user |
| GET | `/{id}/wonAuctions` | USER/ADMIN | List won auctions by user |

### Reports (`/api/v1/reports`)

| Method | Path | Auth | Description |
|---|---|---|---|
| POST | `/` | USER | Submit a report |
| POST | `/{id}/reportImages` | USER | Add image to report |

### Categories (`/api/v1/categories`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | USER/ADMIN | List all categories |

### Admin Auctions (`/api/v1/admin/auctions`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | ADMIN | List all auctions (filterable, sortable) |
| GET | `/{id}` | ADMIN | Get auction details |
| PUT | `/{id}` | ADMIN | Update auction |
| PUT | `/{id}/approve` | ADMIN | Approve or reject submitted auction |
| DELETE | `/{id}` | ADMIN | Delete auction |

### Admin Profiles (`/api/v1/admin/profiles`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | ADMIN | List all profiles (filterable) |
| PUT | `/{id}` | ADMIN | Change user status (ban/unban) |

### Admin Reports (`/api/v1/admin/reports`)

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/` | ADMIN | List all reports (filterable) |
| DELETE | `/{id}` | ADMIN | Delete report |
| POST | `/{id}/results` | ADMIN | Judge a report (accept/reject) |

### WebSocket

| Path | Protocol | Description |
|---|---|---|
| `/ws` | STOMP over SockJS | WebSocket endpoint |
| `/topic/auctions/{id}/bids` | SUBSCRIBE | Real-time bid updates |
| `/queue/notifications` | SUBSCRIBE (user-specific) | Real-time notifications |

## Auction Lifecycle

```
DRAFT → PENDING → QUEUED → OPENING → ENDED
  |                                   |
  +→ CANCELED ←───────────────────────+
```

1. **DRAFT** — User creates auction with item and images
2. **PENDING** — User submits for admin review
3. **QUEUED** — Admin approves; Quartz jobs scheduled for start/end
4. **OPENING** — Auction is live; users place bids in real-time via WebSocket
5. **ENDED** — Automatically when `timeEnd` arrives; highest bidder wins
6. **CANCELED** — Admin rejects, no approval before `timeStart`, or no bids received

## Testing

### Unit Tests

Unit tests are pure Java tests with no Spring context. They use in-memory implementations of ports for fast execution.

```bash
# Run all unit tests
mvn test

# Run tests for a specific context
mvn test -Dtest="com.ghtk.onlinebiddingproject.domain.model.auction.*"
mvn test -Dtest="com.ghtk.onlinebiddingproject.application.impl.*"
```

### Integration Tests

Integration tests use Testcontainers with MySQL for database testing.

```bash
# Run integration tests
mvn verify -Dspring.profiles.active=integration

# Run specific integration test
mvn verify -Dtest="com.ghtk.onlinebiddingproject.adapter.in.auction.AuctionControllerIT"
```

### Test Structure

- **Domain tests**: Pure unit tests for entities, value objects, and domain services
- **Application tests**: Unit tests for application services with in-memory port implementations
- **Adapter tests**: Integration tests for REST controllers and repository implementations
- **Infrastructure tests**: Integration tests for security, WebSocket, and Quartz configurations

### Running Tests with Docker

```bash
# Start test database
docker-compose -f docker-compose.test.yml up -d

# Run all tests
mvn verify

# Stop test database
docker-compose -f docker-compose.test.yml down
```

## Documentation

- Context Map: `.docs/contexts/CONTEXT-MAP.md`
- Bounded Contexts: `.docs/contexts/`
- Architecture Decision Records: `.docs/adrs/`
- PRD: `.docs/prds/0001-PRD-java17-migration.md`
- Hexagonal DDD Refactoring ADR: `.docs/adrs/0011-hexagonal-ddd-refactoring.md`
