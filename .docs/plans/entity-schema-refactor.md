# Entity-Schema Refactoring Plan

## Goal

Align all JPA entities with the MySQL schema, update domain models to be flat (IDs only), and update mappers to handle the new entity relationships using `EntityManager`.

---

## Key Decisions

| Decision | Choice |
|----------|--------|
| Entity vs SQL | Entity must match SQL schema |
| Relationships | Use `@ManyToOne`/`@OneToMany`/`@OneToOne` wherever FK exists |
| Domain models | Flat with IDs only (no rich nested objects) |
| Status/type columns | SQL: `INT` → `VARCHAR(50)`, entities: `@Enumerated(EnumType.STRING)` |
| Mappers | Use `EntityManager.getReference()` for `toEntity()` conversions |
| Lazy loading | Fetch joins in repository when needed, not `EAGER` |
| Back-references | Only for `@OneToOne` owned side; no back-refs for VerificationToken, Notifications |
| `@ManyToMany` | Not used; join tables are standalone entities |

---

## Phase 1: SQL Schema Changes

### File: `mysql/onlineBiddingProject.sql`

#### 1.1 Change status/type columns from INT to VARCHAR

```sql
ALTER TABLE `auction` MODIFY `status` varchar(50) NOT NULL;
ALTER TABLE `profile` MODIFY `status` varchar(50) NOT NULL;
ALTER TABLE `report_result` MODIFY `result` varchar(50) NOT NULL;
ALTER TABLE `review_result` MODIFY `result` varchar(50) NOT NULL;
```

#### 1.2 Update seed data

```sql
-- profile.status: use enum names
UPDATE `profile` SET `status` = 'ACTIVE' WHERE `status` = '2';
UPDATE `profile` SET `status` = 'INACTIVE' WHERE `status` = '0';
-- (map all integer values to their corresponding enum string names)

-- auction.status: use enum names
UPDATE `auction` SET `status` = 'OPENING' WHERE `status` = <int>;
-- (map all integer values to their corresponding enum string names)

-- report_result.result: use enum names
UPDATE `report_result` SET `result` = 'ACCEPTED' WHERE `result` = <int>;
UPDATE `report_result` SET `result` = 'REJECTED' WHERE `result` = <int>;

-- review_result.result: use enum names
UPDATE `review_result` SET `result` = 'ACCEPTED' WHERE `result` = <int>;
UPDATE `review_result` SET `result` = 'REJECTED' WHERE `result` = <int>;
```

---

## Phase 2: Entity Refactoring

### 2.1 `AuctionEntity.java`

**Add relationships:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UserEntity user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "category_id")
private CategoryEntity category;

@OneToOne(mappedBy = "auction", fetch = FetchType.LAZY)
private ItemEntity item;

@OneToOne(mappedBy = "auction", fetch = FetchType.LAZY)
private WinnerEntity winner;

@OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
private List<BidEntity> bids = new ArrayList<>();
```

**Remove:** bare `Integer userId`, `Integer categoryId` fields.

---

### 2.2 `BidEntity.java`

**Remove:** `bidTime` field.

**Add timestamps:**
```java
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

@PreUpdate
protected void onUpdate() { updatedAt = LocalDateTime.now(); }
```

**Add relationships:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private UserEntity user;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id", nullable = false)
private AuctionEntity auction;
```

**Remove:** bare `Integer userId`, `Integer auctionId` fields.

---

### 2.3 `WinnerEntity.java`

**Remove:** `userId` field.

**Add relationships:**
```java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "bid_id", nullable = false)
private BidEntity bid;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id", nullable = false)
private AuctionEntity auction;
```

**Remove:** bare `Integer bidId`, `Integer auctionId` fields.

---

### 2.4 `ProfileEntity.java`

**Fix email:**
```java
// Remove @Column(nullable = false) from email
private String email;
```

**Enum as String:**
```java
@Column(nullable = false)
@Enumerated(EnumType.STRING)
private UserStatus status;
```

**Add timestamps:**
```java
@Column(name = "created_at")
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

@PreUpdate
protected void onUpdate() { updatedAt = LocalDateTime.now(); }
```

---

### 2.5 `ItemEntity.java`

**Add timestamps:**
```java
@Column(name = "created_at")
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

@PreUpdate
protected void onUpdate() { updatedAt = LocalDateTime.now(); }
```

**Add relationship:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id", nullable = false)
private AuctionEntity auction;
```

**Remove:** bare `Integer auctionId` field.

---

### 2.6 `NotificationEntity.java` — Complete Rewrite

```java
@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "notification_type", nullable = false)
    private String notificationType;

    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationAuctionEntity> auctionLinks = new ArrayList<>();

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NotificationReportEntity> reportLinks = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
```

**Removed:** `type` (enum), `message`, `auctionId`, `reportId`.
**Added:** `notificationType` (String), `entityType` (String), `profile` (ManyToOne), `auctionLinks` (OneToMany), `reportLinks` (OneToMany), timestamps.

---

### 2.7 `NotificationAuctionEntity.java`

**Add back-reference:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "notification_id", nullable = false)
private NotificationEntity notification;
```

**Remove:** bare `Integer notificationId` field.

---

### 2.8 `NotificationReportEntity.java`

**Add back-reference:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "notification_id", nullable = false)
private NotificationEntity notification;
```

**Remove:** bare `Integer notificationId` field.

---

### 2.9 `AuctionUserEntity.java` — New Standalone Entity

```java
@Entity
@Table(name = "auction_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id", nullable = false)
    private AuctionEntity auction;
}
```

---

### 2.10 `ReportEntity.java`

**Add relationships:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_reporter_id", nullable = false)
private UserEntity reporter;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_reported_id", nullable = false)
private UserEntity reported;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id")
private AuctionEntity auction;

@OneToOne(mappedBy = "report", fetch = FetchType.LAZY)
private ReportResultEntity resultEntity;
```

**Remove:** bare `Integer reporterId`, `Integer reportedId`, `Integer auctionId` fields; old incorrect `@OneToOne` join.

---

### 2.11 `ReportResultEntity.java`

**Fix join and enum:**
```java
@Column(name = "result", nullable = false)
@Enumerated(EnumType.STRING)
private ReportResultStatus result;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "admin_id", nullable = false)
private AdminEntity admin;

@OneToOne
@JoinColumn(name = "report_id", nullable = false)
private ReportEntity report;
```

**Add timestamps:**
```java
@Column(name = "created_at")
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

@PreUpdate
protected void onUpdate() { updatedAt = LocalDateTime.now(); }
```

**Remove:** bare `Integer adminId`, `Integer reportId` fields; old `status` field name.

---

### 2.12 `ReviewResultEntity.java`

**Enum as String:**
```java
@Column(name = "result", nullable = false)
@Enumerated(EnumType.STRING)
private ReviewResultStatus result;
```

**Add relationships:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "admin_id", nullable = false)
private AdminEntity admin;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "auction_id", nullable = false)
private AuctionEntity auction;
```

**Add timestamps:**
```java
@Column(name = "created_at")
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

@PreUpdate
protected void onUpdate() { updatedAt = LocalDateTime.now(); }
```

**Remove:** bare `Integer adminId`, `Integer auctionId` fields.

---

### 2.13 `ReportImageEntity.java`

**Add relationship:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "report_id", nullable = false)
private ReportEntity report;
```

**Remove:** bare `Integer reportId` field.

---

### 2.14 `VerificationTokenEntity.java`

**Add relationship:**
```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "profile_id", nullable = false)
private ProfileEntity profile;
```

**Remove:** bare `Integer profileId` field. No back-reference on `ProfileEntity`.

---

### 2.15 Unchanged Entities

- `AdminEntity` — no changes
- `UserEntity` — no changes
- `RoleEntity` — no changes
- `CategoryEntity` — no changes
- `ItemImageEntity` — no changes

---

## Phase 3: New Domain Models

### 3.1 `ReviewResult.java`

```java
package com.ghtk.onlinebiddingproject.domain.model.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReviewResult {
    private Integer id;
    private ReviewResultStatus status;
    private Integer adminId;
    private Integer auctionId;

    public static ReviewResult judge(Integer adminId, ReviewResultStatus status) {
        return new ReviewResult(null, status, adminId, null);
    }
}
```

### 3.2 `ReviewResultStatus.java`

```java
package com.ghtk.onlinebiddingproject.domain.model.report;

public enum ReviewResultStatus {
    ACCEPTED,
    REJECTED;

    public boolean isAccepted() { return this == ACCEPTED; }
    public boolean isRejected() { return this == REJECTED; }
}
```

### 3.3 `ReviewResultRepository.java`

```java
package com.ghtk.onlinebiddingproject.domain.model.report;

import java.util.Optional;

public interface ReviewResultRepository {
    ReviewResult save(ReviewResult result);
    Optional<ReviewResult> findByAuctionId(Integer auctionId);
}
```

---

## Phase 4: Mapper Updates

All mappers are in `adapter/out/repository/database/mapper/`.

### 4.1 `AuthMapper.java`

- Handle nullable `email` in `toDomain()`
- `status` is now `@Enumerated(EnumType.STRING)` — no ordinal conversion needed
- Add `createdAt`/`updatedAt` mapping

### 4.2 `AuctionMapper.java`

**`toDomain()`:**
```java
Auction toDomain(AuctionEntity entity) {
    Auction auction = new Auction();
    auction.setId(entity.getId());
    auction.setDescription(entity.getDescription());
    auction.setTimeStart(entity.getTimeStart());
    auction.setTimeEnd(entity.getTimeEnd());
    auction.setPriceStart(Money.of(entity.getPriceStart()));
    auction.setPriceStep(Money.of(entity.getPriceStep()));
    auction.setHighestPrice(Money.of(entity.getHighestPrice()));
    auction.setStatus(entity.getStatus());
    auction.setOwnerId(entity.getUser().getId());
    auction.setCategoryId(entity.getCategory() != null ? entity.getCategory().getId() : null);
    auction.setItemId(entity.getItem() != null ? entity.getItem().getId() : null);
    return auction;
}
```

**`toEntity()`:** Use `EntityManager.getReference()` for relationships.
```java
AuctionEntity toEntity(Auction domain, EntityManager em) {
    AuctionEntity entity = new AuctionEntity();
    entity.setId(domain.getId());
    entity.setDescription(domain.getDescription());
    entity.setTimeStart(domain.getTimeStart());
    entity.setTimeEnd(domain.getTimeEnd());
    entity.setPriceStart(domain.getPriceStart().getAmount());
    entity.setPriceStep(domain.getPriceStep().getAmount());
    entity.setHighestPrice(domain.getHighestPrice() != null ? domain.getHighestPrice().getAmount() : null);
    entity.setStatus(domain.getStatus());
    entity.setUser(em.getReference(UserEntity.class, domain.getOwnerId()));
    entity.setCategory(domain.getCategoryId() != null ? em.getReference(CategoryEntity.class, domain.getCategoryId()) : null);
    return entity;
}
```

**`Bid`:** Similar pattern — `toDomain()` flattens, `toEntity()` uses `EntityManager`.

**`Winner`:** `toDomain()` flattens, `toEntity()` uses `EntityManager`.

### 4.3 `ItemMapper.java`

**`toDomain()`:**
```java
Item toDomain(ItemEntity entity) {
    Item item = new Item();
    item.setId(entity.getId());
    item.setName(entity.getName());
    item.setDescription(entity.getDescription());
    item.setAmount(entity.getAmount());
    item.setAuctionId(entity.getAuction().getId());
    item.setImages(entity.getImages().stream().map(ItemMapper::toDomainImage).collect(Collectors.toList()));
    return item;
}
```

**`toEntity()`:** Use `EntityManager.getReference(ItemEntity.class, domain.getAuctionId())` for the auction relationship.

### 4.4 `NotificationMapper.java` — Complete Rewrite

**`toDomain()`:**
```java
Notification toDomain(NotificationEntity entity) {
    Notification notification = new Notification();
    notification.setId(entity.getId());
    notification.setType(NotificationType.valueOf(entity.getNotificationType()));
    notification.setEntityType(entity.getEntityType());
    notification.setSenderProfileId(entity.getProfile() != null ? entity.getProfile().getId() : null);
    notification.setCreatedAt(entity.getCreatedAt());
    notification.setUpdatedAt(entity.getUpdatedAt());
    notification.setRecipientIds(/* from notification_notified join table */);
    notification.setAuctionIds(entity.getAuctionLinks().stream().map(l -> l.getAuction().getId()).collect(Collectors.toList()));
    notification.setReportIds(entity.getReportLinks().stream().map(l -> l.getReport().getId()).collect(Collectors.toList()));
    return notification;
}
```

**`toEntity()`:** Use `EntityManager.getReference()` for profile, auction links, report links.

### 4.5 `ReportMapper.java`

**`toDomain()`:**
```java
Report toDomain(ReportEntity entity) {
    Report report = new Report();
    report.setId(entity.getId());
    report.setDescription(entity.getDescription());
    report.setReporterId(entity.getReporter().getId());
    report.setReportedId(entity.getReported().getId());
    report.setAuctionId(entity.getAuction() != null ? entity.getAuction().getId() : null);
    // Handle resultEntity lazily — only if fetched via join
    return report;
}
```

**`toEntity()`:** Use `EntityManager.getReference()` for reporter, reported, auction.

**Fetch join for ReportResult:**
```java
// In ReportRepository implementation
@Query("SELECT r FROM ReportEntity r LEFT JOIN FETCH r.resultEntity WHERE r.id = :id")
Optional<ReportEntity> findByIdWithResult(@Param("id") Integer id);
```

### 4.6 `ReviewResultMapper.java` — New

```java
package com.ghtk.onlinebiddingproject.adapter.out.repository.database.mapper;

// toDomain() and toEntity() using EntityManager for admin and auction references
```

---

## Phase 5: Verification

1. Run `mvn compile` to verify all changes compile
2. Check for any remaining references to removed fields
3. Verify all `@Enumerated(EnumType.STRING)` mappings match SQL `VARCHAR` columns

---

## Execution Order

1. **Phase 1:** SQL schema changes (INT→VARCHAR, seed data)
2. **Phase 2:** Entity refactoring (2.15 → 2.1, or any order that avoids compile errors)
3. **Phase 3:** New domain models + repository interfaces
4. **Phase 4:** Mapper updates + new mapper
5. **Phase 5:** Verify compilation

---

## Files Modified Summary

| # | File | Action |
|---|------|--------|
| 1 | `mysql/onlineBiddingProject.sql` | Modify |
| 2 | `AuctionEntity.java` | Modify |
| 3 | `BidEntity.java` | Modify |
| 4 | `WinnerEntity.java` | Modify |
| 5 | `ProfileEntity.java` | Modify |
| 6 | `ItemEntity.java` | Modify |
| 7 | `NotificationEntity.java` | Rewrite |
| 8 | `NotificationAuctionEntity.java` | Modify |
| 9 | `NotificationReportEntity.java` | Modify |
| 10 | `AuctionUserEntity.java` | Create |
| 11 | `ReportEntity.java` | Modify |
| 12 | `ReportResultEntity.java` | Modify |
| 13 | `ReviewResultEntity.java` | Modify |
| 14 | `ReportImageEntity.java` | Modify |
| 15 | `VerificationTokenEntity.java` | Modify |
| 16 | `ReviewResult.java` | Create |
| 17 | `ReviewResultStatus.java` | Create |
| 18 | `ReviewResultRepository.java` | Create |
| 19 | `AuthMapper.java` | Modify |
| 20 | `AuctionMapper.java` | Modify |
| 21 | `ItemMapper.java` | Modify |
| 22 | `NotificationMapper.java` | Rewrite |
| 23 | `ReportMapper.java` | Modify |
| 24 | `ReviewResultMapper.java` | Create |
