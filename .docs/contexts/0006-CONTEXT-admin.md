# Context: Admin

## Domain Model

- **Admin** — Admin user (extends Profile via shared primary key)
- **ReviewResult** — Admin review result on auction submissions

## Invariants

- Only users with ADMIN role can access admin endpoints
- Admins must approve auctions before they become active
- Admins can ban/suspend/activate user accounts
- Admins judge reports and determine actions

## Integration Points

- **← Auth**: Admin profiles are created through the auth system
- **→ Auction**: Admins approve/reject auction submissions
- **→ Report**: Admins review and judge reports
- **→ User Management**: Admins manage user statuses

## Key Services

- `AdminAuctionService` — Auction approval/rejection
- `AdminProfileService` — User management, status changes
- `AdminReportService` — Report review and judgment
