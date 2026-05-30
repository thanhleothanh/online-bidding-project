# Context: Item

## Domain Model

- **Item** — Item being auctioned (name, description, amount), one-to-one with Auction
- **ItemImage** — Images for items, stored on Cloudinary

## Invariants

- Each Item belongs to exactly one Auction
- Item images are stored externally on Cloudinary
- Item name and description are required

## Integration Points

- **← Auction**: Items are created as part of the auction creation flow
- **→ Report**: Reports can reference items as evidence

## Key Services

- `ItemService` — CRUD for items and their images, Cloudinary upload
