# Context: Auction

## Domain Model

- **Auction** — Core entity with description, time range (start/end), pricing (start price, step, highest price), status, linked to User and Category
- **Bid** — Bid placed by a user on an auction (amount + timestamp)
- **Winner** — Records winning bid per auction
- **Category** — 11 categories (electronics, fashion, furniture, entertainment, etc.)

## Auction Status State Machine

```
DRAFT (0) → PENDING (1) → QUEUED (2) → OPENING (3) → ENDED (4)
    |                                          |
    +→ CANCELED (-1) ←─────────────────────────+
```

- **DRAFT**: User creates auction with item and images
- **PENDING**: User submits for admin review
- **QUEUED**: Admin approves; Quartz schedules Start and End jobs
- **OPENING**: Auction is live; users place bids via WebSocket
- **ENDED**: Automatically at `timeEnd`; winner is highest bidder
- **CANCELED**: If admin rejects, no approval by `timeStart`, or no bids

## Invariants

- Auction must have exactly one Item
- Bid amount must be ≥ highest_price + price_step
- Auction can only be bid on while in OPENING status
- Winner is determined automatically when auction ends
- If no bids, auction is CANCELED (not ENDED)

## Integration Points

- **← Auth**: Only authenticated users can create auctions
- **→ Item**: Each auction owns one item with images
- **→ Notification**: Auction events (create, bid, end) trigger notifications
- **← Admin**: Admins approve/reject auctions
- **← Quartz**: Jobs automatically start/end/cancel auctions

## Key Services

- `AuctionService` — Full lifecycle CRUD, submit for review, listing
- `BidService` — Place bids, enforce rules, real-time broadcast
- `JobSchedulerService` — Schedule Quartz jobs for auction automation
