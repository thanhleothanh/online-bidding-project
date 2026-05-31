INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (1, 'reporter', 'pass', NULL, 0, 'Reporter', 'ACTIVE', 'reporter@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (2, 'reported', 'pass', NULL, 0, 'Reported', 'ACTIVE', 'reported@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (3, 'admin', 'pass', NULL, 0, 'Admin', 'ACTIVE', 'admin@test.com', NULL, 1, NOW(), NOW());
INSERT IGNORE INTO user (profile_id) VALUES (1);
INSERT IGNORE INTO user (profile_id) VALUES (2);
INSERT IGNORE INTO user (profile_id) VALUES (3);
INSERT IGNORE INTO admin (profile_id) VALUES (3);
INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (10, 1, 1, 'Test Auction', 'DRAFT', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());
