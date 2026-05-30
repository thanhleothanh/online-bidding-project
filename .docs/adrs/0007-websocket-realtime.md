# ADR-0007: WebSocket for Real-Time Notifications

## Status

Accepted

## Context

The bidding platform requires real-time communication for:

- **Bid updates**: All users watching an auction must see new bids instantly
- **Notifications**: Users must receive notifications (new bids, auction status changes) without polling

HTTP polling is inefficient and introduces latency. The platform needs a push-based mechanism.

## Decision

Use Spring WebSocket with STOMP protocol and SockJS fallback:

1. Add `spring-boot-starter-websocket` dependency
2. Configure `WebSocketMessageBrokerConfigurer` with:
   - Simple broker on `/topic` and `/queue`
   - Application destination prefix `/app`
   - User destination prefix `/user`
   - SockJS endpoint at `/ws`
3. Implement `WebSocketChannelInterceptor` for JWT-based authentication on WebSocket connections
4. Use `SimpMessagingTemplate` to send messages:
   - `/topic/auctions/{auctionId}/bids` — broadcast bid updates to all subscribers
   - `/queue/notifications` — send user-specific notifications
5. `NotificationService.notifyThroughSocket()` iterates over `NotificationNotified` records and sends to each recipient's WebSocket session

## Consequences

- **Positive**: Sub-second latency for bid updates and notifications
- **Positive**: STOMP provides a structured messaging protocol (subscribe, send, topic)
- **Positive**: SockJS fallback ensures compatibility with older browsers and restrictive proxies
- **Negative**: WebSocket connections are stateful; requires sticky sessions or external state management in multi-instance deployments
- **Negative**: Added complexity in authentication (custom interceptor for STOMP CONNECT frame)
- **Neutral**: Notifications are also persisted in the database, so clients can fetch missed notifications via REST
