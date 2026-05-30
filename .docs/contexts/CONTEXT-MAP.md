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
