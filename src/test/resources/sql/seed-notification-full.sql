SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE bid;
TRUNCATE TABLE winner;
TRUNCATE TABLE auction;
TRUNCATE TABLE auction_user;
TRUNCATE TABLE item_image;
TRUNCATE TABLE item;
TRUNCATE TABLE notification_notified;
TRUNCATE TABLE notification_auction;
TRUNCATE TABLE notification_report;
TRUNCATE TABLE notification;
TRUNCATE TABLE review_result;
TRUNCATE TABLE report_result;
TRUNCATE TABLE report_image;
TRUNCATE TABLE report;
TRUNCATE TABLE verification_token;
TRUNCATE TABLE profile;
TRUNCATE TABLE role;
TRUNCATE TABLE user;
TRUNCATE TABLE category;
TRUNCATE TABLE admin;
SET FOREIGN_KEY_CHECKS = 1;

INSERT IGNORE INTO role (id, name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO role (id, name) VALUES (2, 'USER');
INSERT IGNORE INTO category (id, name) VALUES (1, 'Electronics');
INSERT IGNORE INTO category (id, name) VALUES (2, 'Fashion');
INSERT IGNORE INTO category (id, name) VALUES (3, 'Home');

INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (1, 'user1', 'pass', NULL, 0, 'User1', 'ACTIVE', 'user1@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (2, 'user2', 'pass', NULL, 10, 'User2', 'ACTIVE', 'user2@test.com', NULL, 2, NOW(), NOW());
INSERT IGNORE INTO profile (id, username, password, bio, legitimate_score, name, status, email, image_url, role_id, created_at, updated_at)
VALUES (3, 'admin', 'pass', NULL, 0, 'Admin', 'ACTIVE', 'admin@test.com', NULL, 1, NOW(), NOW());

INSERT IGNORE INTO user (profile_id) VALUES (1);
INSERT IGNORE INTO user (profile_id) VALUES (2);
INSERT IGNORE INTO user (profile_id) VALUES (3);
INSERT IGNORE INTO admin (profile_id) VALUES (3);

INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (1, 1, 1, 'Test Auction 1', 'OPENING', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());
INSERT IGNORE INTO auction (id, user_id, category_id, description, status, time_start, time_end, price_start, price_step, created_at, updated_at)
VALUES (2, 2, 1, 'Test Auction 2', 'OPENING', DATE_ADD(NOW(), INTERVAL 1 DAY), DATE_ADD(NOW(), INTERVAL 7 DAY), 1000.0, 100.0, NOW(), NOW());

INSERT IGNORE INTO report (id, user_reporter_id, user_reported_id, auction_id, description, created_at, updated_at)
VALUES (10, 1, 2, 1, 'Test report', NOW(), NOW());
