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
