# Context Map — Online Bidding Project

## Bounded Contexts

```
┌─────────────────────────────────────────────────────────┐
│                      Auth Context                        │
│  Registration, Login, JWT, Email Verification            │
│  Profile/User/Admin creation                             │
└──────────┬──────────────────────────────┬───────────────┘
           │                              │
           │ creates                      │ authenticates
           ▼                              ▼
┌──────────────────────┐    ┌─────────────────────────────┐
│   Auction Context     │◄──│      Admin Context           │
│  Lifecycle, Bidding   │   │  Approval, User Management   │
│  Winner Determination │   │  Report Judgment              │
└──────┬───────────────┘   └──────────┬──────────────────┘
       │                              │
       │ owns items                   │ judges reports
       ▼                              ▼
┌──────────────────────┐    ┌─────────────────────────────┐
│    Item Context       │    │     Report Context           │
│  CRUD, Images         │    │  Submission, Review           │
│  Cloudinary storage   │    │  Evidence images              │
└───────────────────────┘    └─────────────────────────────┘
                                      │
                                      │ triggers
                                      ▼
                             ┌─────────────────────────────┐
                             │  Notification Context        │
                             │  Real-time push (WebSocket)  │
                             │  Entity-linked notifications │
                             └─────────────────────────────┘
```

## Context Relationships

| From | To | Relationship |
|---|---|---|
| Auth | Auction | Auth creates Users who create Auctions |
| Auth | Admin | Auth creates Admin profiles |
| Auction | Item | Each Auction has one Item |
| Auction | Notification | Auction events trigger notifications |
| Admin | Auction | Admins approve/reject auctions |
| Admin | Report | Admins judge reports |
| Report | Notification | Report events trigger notifications |
| Item | Report | Reports can reference Items |

## Shared Kernel

- **Profile** entity is shared across Auth, Admin, and Notification contexts
- **AuctionStatusConstants** enum is shared across Auction and Admin contexts
- **UserStatusConstants** enum is shared across Auth and Admin contexts

## Hexagonal Architecture Package Locations

### Domain Layer (Business Logic)
```
domain/
├── model/
│   ├── auction/          # Auction, Bid, Winner, Category
│   ├── auth/             # Profile, User, Admin, Role
│   ├── item/             # Item, ItemImage
│   ├── notification/     # Notification, NotificationType
│   ├── report/           # Report, ReportResult, ReportImage
│   └── shared/           # Money value object
├── service/              # Domain services (BiddingRulesService, LegitimateScoreService)
├── event/                # Domain events (AuctionCreatedEvent, BidPlacedEvent, etc.)
└── port/                 # Domain ports (WebSocketPort)
```

### Application Layer (Use Cases)
```
application/
├── impl/                 # Service implementations
│   ├── AuctionServiceImpl.java
│   ├── AuthServiceImpl.java
│   ├── BidServiceImpl.java
│   ├── ItemServiceImpl.java
│   ├── NotificationServiceImpl.java
│   ├── ReportServiceImpl.java
│   └── CategoryServiceImpl.java
└── port/                 # Application ports
    ├── JwtPort.java
    └── MailPort.java
```

### Adapter Layer (Driving & Driven)
```
adapter/
├── in/                   # Driving adapters (REST controllers)
│   ├── admin/            # Admin controllers
│   ├── auth/             # Auth controllers
│   ├── auction/          # Auction controllers
│   ├── item/             # Item controllers
│   ├── notification/     # Notification controllers
│   └── report/           # Report controllers
└── out/                  # Driven adapters (database, external services)
    ├── cloudinary/       # Cloudinary adapter
    ├── email/            # Email adapter
    ├── repository/       # Repository implementations
    └── websocket/        # WebSocket adapter
```

### Infrastructure Layer (Cross-cutting Concerns)
```
infrastructure/
├── config/               # Configuration classes
├── events/               # Spring event publisher
├── quartz/               # Quartz job scheduling
├── security/             # Security utilities (CurrentUserUtils, JwtUtils)
├── util/                 # Utility classes
└── websocket/            # WebSocket configuration
```

## Bounded Context Details

### Auth Context
- **Domain**: Profile, User, Admin, Role, UserStatus, VerificationToken
- **Application**: AuthService (login, signup, password change, verification)
- **Adapters**: AuthController, UserRepositoryImpl, EmailAdapter, JwtAdapter
- **Events**: SignupCompleteEvent

### Auction Context
- **Domain**: Auction, Bid, Winner, Category, AuctionStatus, AuctionDraft
- **Application**: AuctionService, BidService, CategoryService
- **Adapters**: AuctionController, BidController, CategoryController, AuctionRepositoryImpl
- **Events**: AuctionCreatedEvent, AuctionApprovedEvent, AuctionEndedEvent, BidPlacedEvent
- **Domain Services**: BiddingRulesService, AuctionLifecycleService

### Item Context
- **Domain**: Item, ItemImage, ImageStoragePort
- **Application**: ItemService
- **Adapters**: ItemController, ItemRepositoryImpl, CloudinaryAdapter
- **Events**: None (synchronous operations)

### Report Context
- **Domain**: Report, ReportResult, ReportImage, ReportResultStatus, ReportQuery
- **Application**: ReportService
- **Adapters**: ReportController, ReportRepositoryImpl, ReportResultRepositoryImpl
- **Events**: None (but triggers LegitimateScoreService)
- **Domain Services**: LegitimateScoreService

### Notification Context
- **Domain**: Notification, NotificationType, NotificationRepository
- **Application**: NotificationService
- **Adapters**: WebSocketPublisherAdapter, NotificationRepositoryImpl
- **Events**: None (but consumes events from other contexts)

### Admin Context
- **Domain**: Uses domain models from other contexts
- **Application**: Uses application services from other contexts
- **Adapters**: AdminAuctionController, AdminProfileController, AdminReportController
- **Events**: None (orchestration layer)
