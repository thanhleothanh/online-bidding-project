# Context: Auth

## Domain Model

- **Profile** — Central user entity with username, email, password (BCrypt), bio, legitimate score, status (BANNED/SUSPENDED/ACTIVE/INACTIVE), role (ADMIN/USER)
- **User** — Regular user (extends Profile via shared primary key)
- **Admin** — Admin user (extends Profile via shared primary key)
- **Role** — Role enum: ADMIN (1), USER (2)
- **VerificationToken** — Email verification token with 10-minute expiry

## Invariants

- Usernames must be 5-30 characters, alphanumeric + underscore/dot only
- Passwords must be at least 8 characters
- Email must be valid format
- Each Profile has exactly one Role
- Verification tokens expire after 10 minutes

## Integration Points

- **→ Auction**: Creates users who can create/participate in auctions
- **→ Admin**: Creates admin profiles who manage the platform
- **→ Notification**: User signup triggers welcome notification
- **← Security**: JWT tokens issued here are validated by the Security context

## Key Services

- `AuthService` — Registration, login, logout, email verification, password changes
- `MailService` — Sends verification emails via Thymeleaf templates
