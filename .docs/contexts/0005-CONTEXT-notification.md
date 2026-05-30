# Context: Notification

## Domain Model

- **Notification** — Notification record with type and entity association
- **NotificationAuction** — Links notifications to auctions
- **NotificationNotified** — Tracks which profiles received a notification
- **NotificationReport** — Links notifications to reports

## Invariants

- Each notification is associated with exactly one entity type (auction or report)
- Notifications are delivered in real-time via WebSocket
- Each profile tracks which notifications they've received

## Integration Points

- **← Auction**: Auction events (create, bid, end) create notifications
- **← Report**: Report events (submit, judge) create notifications
- **→ WebSocket**: Notifications are pushed to clients via STOMP

## Key Services

- `NotificationService` — Create and persist notifications
- `WebSocketService` — Push notifications to connected clients
