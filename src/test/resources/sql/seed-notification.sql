INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (1, 1, 1, 'Test Auction 1', 'OPENING', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());
INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (2, 2, 1, 'Test Auction 2', 'OPENING', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());

INSERT IGNORE INTO report (id, user_reporter_id, user_reported_id, auction_id, description, created_at, updated_at)
VALUES (10, 1, 2, 1, 'Test report', NOW(), NOW());
