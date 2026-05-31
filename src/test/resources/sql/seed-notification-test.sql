INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (1, 'user1', 'pass', NULL, 0, 'User1', 'ACTIVE', 'user1@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (2, 'user2', 'pass', NULL, 0, 'User2', 'ACTIVE', 'user2@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (3, 'user3', 'pass', NULL, 0, 'User3', 'ACTIVE', 'user3@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO user (profile_id) VALUES (1);
INSERT IGNORE INTO user (profile_id) VALUES (2);
INSERT IGNORE INTO user (profile_id) VALUES (3);
INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (1, 1, 1, 'Test Auction', 'OPENING', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());
INSERT IGNORE INTO report (id, user_reporter_id, user_reported_id, auction_id, description, created_at, updated_at)
VALUES (10, 1, 2, 1, 'Test report', NOW(), NOW());
