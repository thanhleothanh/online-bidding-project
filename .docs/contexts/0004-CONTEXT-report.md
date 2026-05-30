# Context: Report

## Domain Model

- **Report** — User report with reporter, reported user, optional auction link, reason
- **ReportImage** — Images attached to reports (stored on Cloudinary)
- **ReportResult** — Admin judgment on reports (action taken)

## Invariants

- A report must have a reporter and a reported user
- Reports can optionally reference an auction
- Only admins can judge reports
- Each report can only be judged once

## Integration Points

- **← Auth**: Reports are submitted by authenticated users
- **← Auction**: Reports can reference auctions
- **→ Notification**: Report submission and judgment trigger notifications
- **← Admin**: Admins review and judge reports

## Key Services

- `ReportService` — Report submission, admin judgment
